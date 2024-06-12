package com.thezayin.ads.builders

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.thezayin.ads.AdBuilder
import com.thezayin.ads.AdStatus

class GoogleInterstitialAdBuilder(private val context: Context, private val id: String) :
    AdBuilder<InterstitialAd>() {
    override val platform: String = "AdMob_Interstitial"
    override fun invoke(onAssign: (AdStatus<InterstitialAd>) -> Unit) {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context, id, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    onAssign(AdStatus.Error(loadAdError))
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    onAssign(AdStatus.Loaded(interstitialAd))
                    interstitialAd.setOnPaidEventListener { adValue ->
                        onPaid?.invoke(adValue)
                    }
                }
            })
    }
}