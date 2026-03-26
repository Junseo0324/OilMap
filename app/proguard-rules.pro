# Project Specific ProGuard Rules

# ---------------------------------------------------------
# Android Default & Kotlin
# ---------------------------------------------------------
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keepattributes Signature
-keepclassmembers class **.R$* {
    public static <fields>;
}

# ---------------------------------------------------------
# Hilt / Dagger
# ---------------------------------------------------------
-keep class dagger.hilt.** { *; }
-keep interface dagger.hilt.** { *; }
-keep @dagger.hilt.android.HiltAndroidApp class *
-keep @dagger.hilt.EntryPoint class *

# ---------------------------------------------------------
# Retrofit & OkHttp
# ---------------------------------------------------------
-keepattributes Signature, InnerClasses, EnclosingMethod
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

# ---------------------------------------------------------
# Kotlinx Serialization
# ---------------------------------------------------------
# @Serializable 클래스 보존 (Data Transfer Objects)
-keep @kotlinx.serialization.Serializable class * { *; }
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
}

# ---------------------------------------------------------
# Room
# ---------------------------------------------------------
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# ---------------------------------------------------------
# Google Play Services (Maps, Ads, Location)
# ---------------------------------------------------------
-keep class com.google.android.gms.** { *; }
-keep interface com.google.android.gms.** { *; }

# AdMob
-keep class com.google.android.gms.ads.** { *; }
-keep public class com.google.android.gms.ads.purchase.InAppPurchaseListener
-keep public class com.google.android.gms.ads.measurement.DynamiteMeasurementEndpoints

# Google Maps
-keep class com.google.android.libraries.maps.** { *; }
-keep interface com.google.android.libraries.maps.** { *; }

# ---------------------------------------------------------
# Proj4j (Coordinate Conversion)
# ---------------------------------------------------------
-keep class org.locationtech.proj4j.** { *; }

# ---------------------------------------------------------
# Optimization Settings
# ---------------------------------------------------------
-repackageclasses ''
-allowaccessmodification
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*