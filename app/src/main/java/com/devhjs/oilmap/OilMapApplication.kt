package com.devhjs.oilmap

import android.app.Application
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OilMapApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // AdMob 초기화
        MobileAds.initialize(this) {}
    }
}
