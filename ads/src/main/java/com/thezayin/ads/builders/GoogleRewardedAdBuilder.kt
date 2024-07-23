package com.thezayin.ads.builders

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.thezayin.ads.AdBuilder
import com.thezayin.ads.AdStatus

class GoogleRewardedAdBuilder(private val context: Context, private val id: String,) :
    AdBuilder<RewardedAd> {
    override fun invoke(onAssign: (AdStatus<RewardedAd>) -> Unit) {
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(context, id, adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    onAssign(AdStatus.Error(error))
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    super.onAdLoaded(rewardedAd)
                    onAssign(AdStatus.Loaded(rewardedAd))

                    rewardedAd.setOnPaidEventListener { adValue -> }
                }
            })
    }
}