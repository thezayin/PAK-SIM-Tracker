package com.thezayin.paksimdetails.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.framework.ads.showAppOpenAd
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Constants
import com.thezayin.paksimdetails.navigation.NavHost
import com.thezayin.paksimdetails.ui.theme.PakSimDetailsTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val googleManager: GoogleManager by inject()
    private val remoteConfig: RemoteConfig by inject()
    private val analytics: Analytics by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC)
        if (remoteConfig.adConfigs.initAds) {
            googleManager.init(this)
            googleManager.initOnLastConsent()
        }
        setContent {
            PakSimDetailsTheme {
                val navController = rememberNavController()
                NavHost(navController = navController)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        this.showAppOpenAd(
            googleManager = googleManager,
            analytics = analytics,
            showAd = remoteConfig.adConfigs.appOpenAd
        )
    }
}
