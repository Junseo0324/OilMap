package com.devhjs.oilmap.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devhjs.oilmap.data.local.entity.StationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStation(station: StationEntity)

    @Query("SELECT * FROM stations WHERE stationId = :stationId")
    suspend fun getStationById(stationId: String): StationEntity?

    @Query("SELECT * FROM stations WHERE isFavorite = 1")
    fun getFavoriteStations(): Flow<List<StationEntity>>

    @Query("UPDATE stations SET isFavorite = :isFavorite, lastUpdated = :lastUpdated WHERE stationId = :stationId")
    suspend fun updateFavoriteStatus(stationId: String, isFavorite: Boolean, lastUpdated: Long)

    @Query("DELETE FROM stations WHERE isFavorite = 0 AND lastUpdated < :threshold")
    suspend fun deleteOldCache(threshold: Long)

    @Query("SELECT * FROM stations WHERE (x BETWEEN :minX AND :maxX) AND (y BETWEEN :minY AND :maxY)")
    suspend fun getStationsInRange(minX: Double, maxX: Double, minY: Double, maxY: Double): List<StationEntity>
}
