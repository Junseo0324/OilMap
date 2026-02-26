package com.devhjs.oilmap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devhjs.oilmap.data.local.dao.StationDao
import com.devhjs.oilmap.data.local.entity.StationEntity

@Database(
    entities = [StationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StationDatabase : RoomDatabase() {
    abstract val stationDao: StationDao

    companion object {
        const val DATABASE_NAME = "oilmap.db"
    }
}
