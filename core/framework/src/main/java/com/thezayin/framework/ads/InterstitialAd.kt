package com.thezayin.framework.ads

import android.app.Activity
import android.util.Log
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Activity.interstitialAd(
    scope: CoroutineScope,
    googleManager: GoogleManager,
    showAd: Boolean,
    analytics: Analytics,
    showLoadingIndicator: () -> Unit,    // Lambda to show the loading indicator
    hideLoadingIndicator: () -> Unit,    // Lambda to hide the loading indicator
    callBack: (InterstitialAdStatus) -> Unit
) {
    var timeoutJob: Job? = null

    scope.launch(Dispatchers.Main) {
        showInterstitialAd(
            activity = this@interstitialAd,
            boolean = showAd,
            analytics = analytics,
            manager = googleManager,
        ) { adStatus ->
            when (adStatus) {
                is InterstitialAdStatus.AdNotAvailable -> {
                    Log.d("jejeInterstitialAdActivity", "Ad not available")
                    timeoutJob?.cancel()
                    hideLoadingIndicator.invoke()
                    callBack(adStatus)
                }

                is InterstitialAdStatus.Shown -> {
                    Log.d("jejeInterstitialAdActivity", "Ad shown")
                    timeoutJob?.cancel()
                    hideLoadingIndicator.invoke()
                    callBack(adStatus)
                }

                is InterstitialAdStatus.AdAvailable -> {
                    Log.d("jejeInterstitialAdActivity", "Ad is loading")
                    showLoadingIndicator.invoke()
                    timeoutJob = scope.launch {
                        delay(5000L) // 5-second timeout
                        Log.d("jejeInterstitialAdActivity", "Ad loading timed out")
                        hideLoadingIndicator.invoke()
                        callBack(InterstitialAdStatus.AdNotAvailable)
                    }
                }
            }
        }
    }
}