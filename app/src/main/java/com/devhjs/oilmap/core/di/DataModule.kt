package com.devhjs.oilmap.core.di

import android.content.Context
import androidx.room.Room
import com.devhjs.oilmap.data.local.StationDatabase
import com.devhjs.oilmap.data.local.dao.StationDao
import com.devhjs.oilmap.data.remote.api.OpinetService
import com.devhjs.oilmap.data.repository.StationRepositoryImpl
import com.devhjs.oilmap.domain.repository.StationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

import com.devhjs.oilmap.BuildConfig
import com.devhjs.oilmap.data.repository.MockStationRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideStationDatabase(@ApplicationContext context: Context): StationDatabase {
        return Room.databaseBuilder(
            context,
            StationDatabase::class.java,
            StationDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideStationDao(database: StationDatabase): StationDao {
        return database.stationDao
    }

    @Provides
    @Singleton
    fun provideStationRepository(
        api: OpinetService,
        dao: StationDao
    ): StationRepository {
        return if (BuildConfig.IS_DEV) {
            MockStationRepositoryImpl()
        } else {
            StationRepositoryImpl(api, dao)
        }
    }
}

