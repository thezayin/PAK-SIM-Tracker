package com.thezayin.paksimdetails.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.thezayin.paksimdetails.framework.admob.domain.repository.AppOpenAdManager
import com.thezayin.paksimdetails.framework.analytics.analytics.Analytics
import com.thezayin.paksimdetails.framework.analytics.events.AnalyticsEvent
import com.thezayin.paksimdetails.framework.remote.RemoteConfig
import com.thezayin.paksimdetails.framework.utils.Constants
import com.thezayin.paksimdetails.navigation.NavHost
import com.thezayin.paksimdetails.theme.PakSimDetailsTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val remoteConfig: RemoteConfig by inject()
    private val analytics: Analytics by inject()
    private val adManager: AppOpenAdManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadAd()
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC)
        setContent {
            PakSimDetailsTheme {
                val navController = rememberNavController()
                NavHost(navController = navController)
            }
        }
    }

    fun loadAd() {
        adManager.loadAd(this)
    }

    override fun onStart() {
        super.onStart()
        adManager.showAd(
            this,
            showAd = remoteConfig.adConfigs.resumeAppOpenAd,
            adImpression = {
                analytics.logEvent(
                    AnalyticsEvent.AdImpressionEvent(
                        event = "app_open_ad",
                        provider = "admob",
                    )
                )
            },
            onNext = {}
        )
    }
}
