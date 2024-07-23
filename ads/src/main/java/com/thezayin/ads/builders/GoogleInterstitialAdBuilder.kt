package com.thezayin.ads.builders

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.thezayin.ads.AdBuilder
import com.thezayin.ads.AdStatus

class GoogleInterstitialAdBuilder(private val context: Context, private val id: String) :
    AdBuilder<InterstitialAd> {
    override fun invoke(onAssign: (AdStatus<InterstitialAd>) -> Unit) {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(context, id, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    onAssign(AdStatus.Error(error))
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    onAssign(AdStatus.Loaded(interstitialAd))
                    interstitialAd.setOnPaidEventListener{  adValue -> }
                }
            })
    }
}