package com.devhjs.oilmap.core.di

import android.app.Application
import com.devhjs.oilmap.BuildConfig
import com.devhjs.oilmap.data.location.DefaultLocationTracker
import com.devhjs.oilmap.data.location.MockLocationTracker
import com.devhjs.oilmap.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        application: Application
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application)
    }

    @Provides
    @Singleton
    fun provideLocationTracker(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): LocationTracker {
        return if (BuildConfig.IS_DEV) {
            MockLocationTracker()
        } else {
            DefaultLocationTracker(
                locationClient = fusedLocationProviderClient,
                application = application
            )
        }
    }
}
