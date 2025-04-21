package com.thezayin.paksimdetails.framework.admob.data.repository

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.thezayin.paksimdetails.framework.admob.domain.repository.AppOpenAdManager
import timber.log.Timber

/**
 * Implementation of the AppOpenAdManager interface to manage the loading, showing,
 * and handling of App Open ads using Google's AdMob SDK.
 */
class AppOpenAdManagerImpl : AppOpenAdManager {

    private var appOpenAd: AppOpenAd? = null
    private val adId: String = "ca-app-pub-2913057115284606/6492388900"

    // Flag to track if the ad is currently showing
    private var isAdShowing = false

    override fun loadAd(activity: Activity) {
        Timber.tag("AppOpenAd").d("Loading app open ad")

        if (appOpenAd == null) {
            val adRequest = AdRequest.Builder().build()
            AppOpenAd.load(
                activity,
                adId,
                adRequest,
                object : AppOpenAd.AppOpenAdLoadCallback() {
                    override fun onAdLoaded(ad: AppOpenAd) {
                        Timber.tag("AppOpenAd").d("App Open Ad loaded successfully")
                        appOpenAd = ad
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Timber.tag("AppOpenAd").d("App Open Ad failed to load: ${loadAdError.message}")
                        appOpenAd = null
                    }
                })
        }
    }

    override fun showAd(
        activity: Activity,
        showAd: Boolean,
        adImpression: () -> Unit,
        onNext: () -> Unit
    ) {
        if (showAd) {
            if (isAdShowing) {
                Timber.tag("AppOpenAd").d("Ad is already being shown. Skipping new ad.")
                return
            }

            appOpenAd?.let { ad ->
                isAdShowing = true
                ad.show(activity)

                ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdImpression() {
                        adImpression()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Timber.tag("AppOpenAd").d("App Open Ad failed to show: ${adError.message}")
                        isAdShowing = false
                        onNext()
                    }

                    override fun onAdDismissedFullScreenContent() {
                        Timber.tag("AppOpenAd").d("App Open Ad dismissed.")
                        isAdShowing = false
                        onNext()
                    }

                    override fun onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent()
                    }
                }
            } ?: run {
                Timber.tag("AppOpenAd").d("App Open Ad not loaded. Proceeding to next action.")
                onNext()
            }
        } else {
            onNext()
        }
    }
}