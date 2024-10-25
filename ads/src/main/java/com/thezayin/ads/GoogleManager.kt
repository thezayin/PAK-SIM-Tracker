package com.thezayin.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.thezayin.ads.builders.GoogleAppOpenAdBuilder
import com.thezayin.ads.builders.GoogleInterstitialAdBuilder
import com.thezayin.ads.builders.GoogleNativeAdBuilder
import com.thezayin.ads.builders.GoogleRewardedAdBuilder
import com.thezayin.ads.builders.GoogleRewardedInterstitialAdBuilder
import com.thezayin.ads.ump.ConsentManager
import com.thezayin.ads.utils.AdUnit
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.analytics.events.AnalyticsEvent

class GoogleManager(
    private val context: Context,
    private val consentManager: ConsentManager,
    private val analytics: Analytics

) {
    private val debug get() = BuildConfig.DEBUG
    private var googleInterAd: GoogleAd<InterstitialAd>? = null
    private var googleAppOpen: GoogleAd<AppOpenAd>? = null
    private var googleNativeAd: GoogleAd<NativeAd>? = null
    private var googleRewardedAd: GoogleAd<RewardedAd>? = null
    private var googleRewardedInterstitialAd: GoogleAd<RewardedInterstitialAd>? = null

    private val testDeviceIds: List<String> = listOf(
        AdRequest.DEVICE_ID_EMULATOR, "990C1C4A58DB7FED6AF5D9A33E3DD1FF",//Samsung,
        "65B571F43583ED2ABB211D2965BE3E11"
    )

    fun init(activity: Activity) {
        loadAds()
    }

    fun initOnLastConsent() = consentManager.ifCanRequestAds { loadAds() }

    private fun loadAds() {
        MobileAds.initialize(context) {
            Log.d("GoogleManager", "MobileAds initialized.")
        }
        if (debug) {
            MobileAds.setRequestConfiguration(
                RequestConfiguration.Builder()
                    .setTestDeviceIds(testDeviceIds)
                    .build()
            )
            Log.d("GoogleManager", "Test device IDs set.")
        }

        // Correctly instantiate ad builders
        googleRewardedInterstitialAd = GoogleAd(
            GoogleRewardedInterstitialAdBuilder(
                context = context,
                id = AdUnit.rewardedInterstitial.resolve(debug),
                analytics = analytics
            ).withAnalytics()
        )

        googleRewardedAd = GoogleAd(
            GoogleRewardedAdBuilder(
                context = context,
                id = AdUnit.rewarded.resolve(debug),
                analytics = analytics
            ).withAnalytics()
        )

        googleInterAd = GoogleAd(
            GoogleInterstitialAdBuilder(
                context = context,
                id = AdUnit.interstitial.resolve(debug),
                analytics = analytics
            ).withAnalytics()
        )

        googleAppOpen = GoogleAd(
            GoogleAppOpenAdBuilder(
                context = context,
                id = AdUnit.appOpen.resolve(debug),
                analytics = analytics
            ).withAnalytics()
        )

        googleNativeAd = GoogleAd(
            GoogleNativeAdBuilder(
                context = context,
                id = AdUnit.native.resolve(debug),
                analytics = analytics
            ).withAnalytics()
        )

        // Pre-load all ad types
        googleRewardedInterstitialAd?.loadAd()
        googleRewardedAd?.loadAd()
        googleInterAd?.loadAd()
        googleAppOpen?.loadAd()
        googleNativeAd?.loadAd()

        Log.d("GoogleManager", "All ad builders initialized and ads are loading.")
    }

    private fun <T> ((Context, String, Analytics) -> AdBuilder<T>).from(unit: AdUnit) = GoogleAd(
        this(context, unit.resolve(debug), analytics).withAnalytics()
    )

    private fun <T> AdBuilder<T>.withAnalytics() = apply {
        onPaid {
            analytics.logEvent(
                AnalyticsEvent.AdPaidEvent(
                    event = "AdPaid",
                    provider = platform,
                    value = (it.valueMicros / 1000000.0).toString()
                )
            )
        }
    }

    fun createAppOpenAd(onLoading: () -> Unit, onAdReady: (AppOpenAd?) -> Unit) {
        googleAppOpen?.get(
            onLoading = onLoading,
            onAdReady = onAdReady
        ) ?: run {
            Log.e("GoogleManager", "AppOpenAd builder is not initialized.")
            onAdReady(null)
        }
    }

    fun createInterstitialAd(onLoading: () -> Unit, onAdReady: (InterstitialAd?) -> Unit) {
        googleInterAd?.get(
            onLoading = onLoading,
            onAdReady = onAdReady
        ) ?: run {
            Log.e("GoogleManager", "InterstitialAd builder is not initialized.")
            onAdReady(null)
        }
    }

    fun createNativeAd(onLoading: () -> Unit, onAdReady: (NativeAd?) -> Unit) {
        googleNativeAd?.get(
            onLoading = onLoading,
            onAdReady = onAdReady
        ) ?: run {
            Log.e("GoogleManager", "NativeAd builder is not initialized.")
            onAdReady(null)
        }
    }

    fun createRewardedAd(onLoading: () -> Unit, onAdReady: (RewardedAd?) -> Unit) {
        googleRewardedAd?.get(
            onLoading = onLoading,
            onAdReady = onAdReady
        ) ?: run {
            Log.e("jejeGoogleManager", "RewardedAd builder is not initialized.")
            onAdReady(null)
        }
    }

    private fun <T : Any?> ifNotSubscribed(block: () -> T?) = block()
}