package com.devhjs.oilmap.data.mapper

import com.devhjs.oilmap.data.local.entity.StationEntity
import com.devhjs.oilmap.data.remote.dto.StationDetailDto
import com.devhjs.oilmap.data.remote.dto.StationDto
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.Station

/**
 * API 응답(DTO)을 로컬 DB 엔티티로 변환
 * 기존 데이터가 있을 경우 즐겨찾기 상태나 좌표 등을 유지하며 가격 정보를 업데이트합니다.
 */
fun StationDto.toEntity(oilType: OilType, existingEntity: StationEntity? = null): StationEntity {
    return StationEntity(
        stationId = stationId,
        name = name,
        brandCode = brandCode,
        x = katecX ?: existingEntity?.x ?: 0.0,
        y = katecY ?: existingEntity?.y ?: 0.0,
        address = existingEntity?.address ?: "",
        tel = existingEntity?.tel ?: "",
        hasCarWash = existingEntity?.hasCarWash ?: false,
        hasMaintenance = existingEntity?.hasMaintenance ?: false,
        hasConvenienceStore = existingEntity?.hasConvenienceStore ?: false,
        isQualityCertified = existingEntity?.isQualityCertified ?: false,
        
        gasolinePrice = if (oilType == OilType.GASOLINE) price else existingEntity?.gasolinePrice ?: 0,
        dieselPrice = if (oilType == OilType.DIESEL) price else existingEntity?.dieselPrice ?: 0,
        premiumGasolinePrice = if (oilType == OilType.PREMIUM_GASOLINE) price else existingEntity?.premiumGasolinePrice ?: 0,
        kerosenePrice = if (oilType == OilType.KEROSENE) price else existingEntity?.kerosenePrice ?: 0,
        lpgPrice = if (oilType == OilType.LPG) price else existingEntity?.lpgPrice ?: 0,
        
        isFavorite = existingEntity?.isFavorite ?: false,
        lastUpdated = System.currentTimeMillis()
    )
}

/**
 * 주유소 상세 정보를 로컬 DB 엔티티로 변환 (상세 정보 업데이트 시 사용)
 */
fun StationDetailDto.toEntity(existingEntity: StationEntity?): StationEntity {
    return StationEntity(
        stationId = stationId,
        name = name,
        brandCode = brandCode,
        x = existingEntity?.x ?: 0.0,
        y = existingEntity?.y ?: 0.0,
        address = newAddress ?: existingEntity?.address ?: "",
        tel = tel ?: existingEntity?.tel ?: "",
        hasCarWash = hasCarWash == "Y",
        hasMaintenance = hasMaintenance == "Y",
        hasConvenienceStore = hasConvenienceStore == "Y",
        isQualityCertified = isQualityCertified == "Y",
        
        gasolinePrice = existingEntity?.gasolinePrice ?: 0,
        dieselPrice = existingEntity?.dieselPrice ?: 0,
        premiumGasolinePrice = existingEntity?.premiumGasolinePrice ?: 0,
        kerosenePrice = existingEntity?.kerosenePrice ?: 0,
        lpgPrice = existingEntity?.lpgPrice ?: 0,
        
        isFavorite = existingEntity?.isFavorite ?: false,
        lastUpdated = System.currentTimeMillis()
    )
}

/**
 * 로컬 DB 엔티티를 도메인 모델로 변환 (UI 노출용)
 */
fun StationEntity.toDomain(oilType: OilType, distance: Double? = null): Station {
    val price = when (oilType) {
        OilType.GASOLINE -> gasolinePrice
        OilType.DIESEL -> dieselPrice
        OilType.PREMIUM_GASOLINE -> premiumGasolinePrice
        OilType.KEROSENE -> kerosenePrice
        OilType.LPG -> lpgPrice
    } ?: 0

    return Station(
        id = stationId,
        name = name,
        brandCode = brandCode,
        price = price,
        distance = distance,
        x = x,
        y = y,
        address = address,
        tel = tel,
        hasCarWash = hasCarWash,
        hasMaintenance = hasMaintenance,
        hasConvenienceStore = hasConvenienceStore,
        isQualityCertified = isQualityCertified,
        isFavorite = isFavorite
    )
}
