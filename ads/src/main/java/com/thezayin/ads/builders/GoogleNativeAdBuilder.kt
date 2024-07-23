package com.thezayin.ads.builders

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.ResponseInfo
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.AdBuilder
import com.thezayin.ads.AdStatus

class GoogleNativeAdBuilder(private val context: Context, private val id: String) :
    AdBuilder<NativeAd> {
    override fun invoke(onAssign: (AdStatus<NativeAd>) -> Unit) {
        val loader = AdLoader.Builder(context, id)
            .forNativeAd {
                onAssign(AdStatus.Loaded(it))
                it.setOnPaidEventListener{  adValue ->

                }
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    onAssign(AdStatus.Error(error))
                }
            })
            .build()

        loader.loadAd(AdRequest.Builder().build())
    }
}