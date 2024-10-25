package com.thezayin.ads.builders

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.thezayin.ads.AdBuilder
import com.thezayin.ads.AdStatus
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.analytics.events.AnalyticsEvent

class GoogleAppOpenAdBuilder(private val context: Context, private val id: String, private val analytics: Analytics) :
    AdBuilder<AppOpenAd>() {
    override val platform: String = "AdMob_AppOpen"
    override fun invoke(onAssign: (AdStatus<AppOpenAd>) -> Unit) {
        val adRequest = AdRequest.Builder().build()
        Log.d("GoogleAppOpenAdBuilder", "Attempting to load AppOpenAd with ID: $id")
        AppOpenAd.load(
            context,
            id,
            adRequest,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    Log.e("GoogleAppOpenAdBuilder", "Failed to load AppOpenAd: ${loadAdError.message}")
                    onAssign(AdStatus.Error(loadAdError))
                }

                override fun onAdLoaded(openAd: AppOpenAd) {
                    super.onAdLoaded(openAd)
                    Log.d("GoogleAppOpenAdBuilder", "AppOpenAd loaded successfully.")
                    onAssign(AdStatus.Loaded(openAd))

                    // Set up the paid event listener
                    openAd.setOnPaidEventListener { adValue ->
                        onPaid?.invoke(adValue)
                        logAdPaidEvent(adValue)
                    }
                }
            }
        )
    }

    private fun logAdPaidEvent(adValue: AdValue) {
        analytics.logEvent(
            AnalyticsEvent.AdPaidEvent(
                event = "AdPaid",
                provider = platform,
                value = (adValue.valueMicros / 1_000_000.0).toString()
            )
        )
        Log.d("GoogleAppOpenAdBuilder", "Ad paid event logged: ${adValue.valueMicros} micros")
    }
}