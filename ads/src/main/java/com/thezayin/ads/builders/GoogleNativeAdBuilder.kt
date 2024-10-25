package com.thezayin.ads.builders

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.AdBuilder
import com.thezayin.ads.AdStatus
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.analytics.events.AnalyticsEvent

class GoogleNativeAdBuilder(
    private val context: Context,
    private val id: String,
    private val analytics: Analytics
) : AdBuilder<NativeAd>() {

    override val platform: String = "AdMob_Native"
    override fun invoke(onAssign: (AdStatus<NativeAd>) -> Unit) {
        Log.d("GoogleNativeAdBuilder", "Attempting to load NativeAd with ID: $id")

        val adLoader = AdLoader.Builder(context, id)
            .forNativeAd { nativeAd ->
                Log.d("GoogleNativeAdBuilder", "NativeAd loaded successfully.")
                onAssign(AdStatus.Loaded(nativeAd))
                setupPaidEvent(nativeAd)
            }
            .withAdListener(object : AdListener() {
                override fun onAdImpression() {
                    super.onAdImpression()
                    Log.d("GoogleNativeAdBuilder", "NativeAd impression recorded.")
                    logAdImpression()
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    Log.e("GoogleNativeAdBuilder", "Failed to load NativeAd: ${error.message}")
                    onAssign(AdStatus.Error(error))
                }
            })
            .build()

        adLoader.loadAd(createAdRequest())
    }

    private fun createAdRequest(): AdRequest {
        return AdRequest.Builder().build()
    }
    private fun setupPaidEvent(nativeAd: NativeAd) {
        nativeAd.setOnPaidEventListener { adValue ->
            Log.d("GoogleNativeAdBuilder", "NativeAd paid event received: ${adValue.valueMicros} micros")
            onPaid?.invoke(adValue)
            logAdPaidEvent(adValue)
        }
    }
    private fun logAdPaidEvent(adValue: AdValue) {
        analytics.logEvent(
            AnalyticsEvent.AdPaidEvent(
                event = "AdPaid",
                provider = platform,
                value = (adValue.valueMicros / 1_000_000.0).toString()
            )
        )
        Log.d("GoogleNativeAdBuilder", "AdPaidEvent logged with value: ${(adValue.valueMicros / 1_000_000.0)}")
    }
    private fun logAdImpression() {
        analytics.logEvent(
            AnalyticsEvent.AdImpressionEvent(
                event = "AdImpression",
                provider = platform
            )
        )
        Log.d("GoogleNativeAdBuilder", "AdImpressionEvent logged.")
    }
}