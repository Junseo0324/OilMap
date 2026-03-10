package com.devhjs.oilmap.presentation.component.ad

import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.devhjs.oilmap.R
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

@Composable
fun NativeAdCard(
    modifier: Modifier = Modifier,
    adUnitId: String = "ca-app-pub-3940256099942544/2247696110"
) {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }

    LaunchedEffect(adUnitId) {
        val adLoader = AdLoader.Builder(context, adUnitId)
            .forNativeAd { ad ->
                nativeAd = ad
            }
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    if (nativeAd != null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, AppColors.Border, RoundedCornerShape(16.dp))
        ) {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { ctx ->
                    val inflater = LayoutInflater.from(ctx)
                    val adView = inflater.inflate(R.layout.ad_native_card, null) as NativeAdView
                    
                    // Assign views
                    adView.headlineView = adView.findViewById(R.id.ad_headline)
                    adView.bodyView = adView.findViewById(R.id.ad_body)
                    adView.iconView = adView.findViewById(R.id.ad_icon)
                    adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
                    
                    adView
                },
                update = { adView ->
                    val ad = nativeAd ?: return@AndroidView
                    
                    (adView.headlineView as? TextView)?.text = ad.headline
                    
                    if (ad.body == null) {
                        adView.bodyView?.visibility = android.view.View.INVISIBLE
                    } else {
                        adView.bodyView?.visibility = android.view.View.VISIBLE
                        (adView.bodyView as? TextView)?.text = ad.body
                    }

                    if (ad.callToAction == null) {
                        adView.callToActionView?.visibility = android.view.View.INVISIBLE
                    } else {
                        adView.callToActionView?.visibility = android.view.View.VISIBLE
                        (adView.callToActionView as? Button)?.text = ad.callToAction
                    }

                    if (ad.icon == null) {
                        adView.iconView?.visibility = android.view.View.GONE
                    } else {
                        (adView.iconView as? ImageView)?.setImageDrawable(ad.icon?.drawable)
                        adView.iconView?.visibility = android.view.View.VISIBLE
                    }
                    
                    adView.setNativeAd(ad)
                }
            )
        }
    }
}
