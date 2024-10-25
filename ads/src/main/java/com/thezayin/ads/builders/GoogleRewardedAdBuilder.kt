package com.thezayin.ads.builders

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.thezayin.ads.AdBuilder
import com.thezayin.ads.AdStatus
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.analytics.events.AnalyticsEvent

class GoogleRewardedAdBuilder(
    private val context: Context,
    private val id: String,
    private val analytics: Analytics
) : AdBuilder<RewardedAd>() {
    override val platform: String = "AdMob_Rewarded"

    override fun invoke(onAssign: (AdStatus<RewardedAd>) -> Unit) {
        val adRequest = AdRequest.Builder().build()
        Log.d("jejeGoogleRewardedAdBuilder", "Attempting to load RewardedAd with ID: $id")
        RewardedAd.load(
            context,
            id,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    Log.e(
                        "jejeGoogleRewardedAdBuilder",
                        "Failed to load RewardedAd: ${loadAdError.message}"
                    )
                    onAssign(AdStatus.Error(loadAdError))
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    super.onAdLoaded(rewardedAd)
                    Log.d("jejeGoogleRewardedAdBuilder", "RewardedAd loaded successfully.")
                    onAssign(AdStatus.Loaded(rewardedAd))
                    rewardedAd.setOnPaidEventListener { adValue ->
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
        Log.d(
            "jejeGoogleRewardedAdBuilder",
            "AdPaidEvent logged: ${adValue.valueMicros / 1_000_000.0} USD"
        )
    }
}