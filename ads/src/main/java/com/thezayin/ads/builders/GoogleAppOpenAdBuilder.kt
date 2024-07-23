package com.thezayin.ads.builders

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import com.thezayin.ads.AdBuilder
import com.thezayin.ads.AdStatus
import com.thezayin.analytics.helpers.AnalyticsHelper

class GoogleAppOpenAdBuilder(private val context: Context, private val id: String) :
    AdBuilder<AppOpenAd> {
    override fun invoke(onAssign: (AdStatus<AppOpenAd>) -> Unit) {
        val adRequest = AdRequest.Builder().build()

        AppOpenAd.load(context, id, adRequest,
            object : AppOpenAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    onAssign(AdStatus.Error(error))
                }

                override fun onAdLoaded(openAd: AppOpenAd) {
                    super.onAdLoaded(openAd)
                    onAssign(AdStatus.Loaded(openAd))
                    openAd.setOnPaidEventListener{  adValue ->

                    }
                }
            })
    }
}