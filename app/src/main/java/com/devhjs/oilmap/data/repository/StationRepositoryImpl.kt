package com.devhjs.oilmap.data.repository

import com.devhjs.oilmap.data.local.dao.StationDao
import com.devhjs.oilmap.data.mapper.toDetailDomain
import com.devhjs.oilmap.data.mapper.toDomain
import com.devhjs.oilmap.data.mapper.toEntity
import com.devhjs.oilmap.data.remote.api.OpinetService
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.domain.model.StationDetail
import com.devhjs.oilmap.domain.repository.StationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.sqrt

class StationRepositoryImpl @Inject constructor(
    private val api: OpinetService,
    private val dao: StationDao
) : StationRepository {

    private val cacheTtl = 6 * 60 * 60 * 1000L // 6시간 캐시 유지

    override suspend fun getAroundStations(
        katecX: Double,
        katecY: Double,
        radius: Int,
        oilType: OilType,
        sortType: SortType
    ): List<Station> {
        
        // 2. 로컬 캐시 확인 (반경 내 유효한 데이터가 있는지)
        val minX = katecX - radius
        val maxX = katecX + radius
        val minY = katecY - radius
        val maxY = katecY + radius
        
        val cachedEntities = dao.getStationsInRange(minX, maxX, minY, maxY)
        
        // 캐시가 유효하려면: 데이터가 존재하고, TTL 이내이며, 요청된 유종의 가격이 실제로 저장되어 있어야 함
        val isCacheValid = cachedEntities.isNotEmpty() && 
                          cachedEntities.all { System.currentTimeMillis() - it.lastUpdated < cacheTtl } &&
                          cachedEntities.any { entity ->
                              when (oilType) {
                                  OilType.GASOLINE -> entity.gasolinePrice > 0
                                  OilType.DIESEL -> entity.dieselPrice > 0
                                  OilType.PREMIUM_GASOLINE -> entity.premiumGasolinePrice > 0
                                  OilType.KEROSENE -> entity.kerosenePrice > 0
                                  OilType.LPG -> entity.lpgPrice > 0
                              }
                          }

        if (isCacheValid) {
            // Data Layer에서는 데이터를 반환하기만 수행 (거리 계산 및 정렬은 UseCase 위임)
            return cachedEntities.map { entity ->
                entity.toDomain(oilType)
            }
        }

        // 3. 캐시가 없거나 만료된 경우 API 호출
        val response = api.getStationsAround(
            x = katecX,
            y = katecY,
            radius = radius,
            prodcd = oilType.code,
            sort = sortType.code
        )

        val stationDtos = response.result.oil
        
        // 4. API 데이터를 로컬 DB에 저장 (동기화 및 캐싱)
        stationDtos.forEach { dto ->
            val existing = dao.getStationById(dto.stationId)
            dao.insertStation(dto.toEntity(oilType, existing))
        }

        // 5. 업데이트된 데이터 도메인 모델로 변환하여 반환
        return stationDtos.map { dto ->
            val entity = dao.getStationById(dto.stationId)!!
            entity.toDomain(oilType, dto.distance)
        }
    }

    override suspend fun getStationDetail(
        stationId: String,
        userKatecX: Double?,
        userKatecY: Double?
    ): StationDetail {
        val existing = dao.getStationById(stationId)
        
        val distance = if (userKatecX != null && userKatecY != null && existing != null && existing.x > 0 && existing.y > 0) {
            sqrt(
                (existing.x - userKatecX) * (existing.x - userKatecX) +
                (existing.y - userKatecY) * (existing.y - userKatecY)
            )
        } else null

        if (existing != null && existing.address.isNotEmpty() && System.currentTimeMillis() - existing.lastUpdated < cacheTtl) {
            return existing.toDetailDomain(distance)
        }

        val response = api.getStationDetail(stationId = stationId)
        val detailDto = response.result.oil.firstOrNull() ?: throw Exception("상세 정보를 찾을 수 없습니다.")
        
        val newEntity = detailDto.toEntity(existing)
        dao.insertStation(newEntity)
        
        val newDistance = if (userKatecX != null && userKatecY != null && newEntity.x > 0 && newEntity.y > 0) {
            sqrt(
                (newEntity.x - userKatecX) * (newEntity.x - userKatecX) +
                (newEntity.y - userKatecY) * (newEntity.y - userKatecY)
            )
        } else null
        
        return newEntity.toDetailDomain(newDistance)
    }

    override suspend fun getLowPriceStations(oilType: OilType, area: String?): List<Station> {
        val response = api.getLowPriceTop(
            prodcd = oilType.code,
            area = area
        )
        
        val stationDtos = response.result.oil
        
        // 최저가 목록도 캐싱함 (다음에 주변 조회 시 활용 가능)
        stationDtos.forEach { dto ->
            val existing = dao.getStationById(dto.stationId)
            dao.insertStation(dto.toEntity(oilType, existing))
        }

        return stationDtos.map { dto ->
            val entity = dao.getStationById(dto.stationId)!!
            entity.toDomain(oilType, dto.distance)
        }
    }

    override fun getFavoriteStations(oilType: OilType): Flow<List<Station>> {
        return dao.getFavoriteStations().map { entities ->
            entities.map { it.toDomain(oilType) }
        }
    }

    override suspend fun toggleFavorite(station: Station) {
        val isFavorite = !station.isFavorite
        dao.updateFavoriteStatus(station.id, isFavorite, System.currentTimeMillis())
    }

    override suspend fun isFavorite(stationId: String): Boolean {
        val entity = dao.getStationById(stationId)
        return entity?.isFavorite ?: false
    }
}
