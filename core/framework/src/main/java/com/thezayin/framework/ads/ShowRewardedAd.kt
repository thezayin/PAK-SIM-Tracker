@file:Suppress("DEPRECATION")

package com.thezayin.framework.ads

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull

fun Activity.showRewardedAd(
    scope: CoroutineScope,
    googleManager: GoogleManager,
    analytics: Analytics,
    showAd: Boolean,
    showLoadingIndicator: () -> Unit,    // Lambda to show the loading indicator
    hideLoadingIndicator: () -> Unit,    // Lambda to hide the loading indicator
    callback: (RewardedAdStatus) -> Unit
) {
    scope.launch(Dispatchers.Main) {
        if (!showAd) {
            Log.d("jejeshowRewardedAd", "Ad request not allowed, returning.")
            callback.invoke(RewardedAdStatus.AdNotAvailable)
            return@launch
        }

        if (!isConnected(this@showRewardedAd)) {
            Log.e("jejeshowRewardedAd", "Not connected to the internet.")
            callback.invoke(RewardedAdStatus.AdNotAvailable)
            return@launch
        }

        // Show loading indicator
        showLoadingIndicator.invoke()
        Log.d("jejeshowRewardedAd", "Loading Rewarded Ad...")

        // Use withTimeoutOrNull to handle the timeout
        val adLoaded = withTimeoutOrNull(5000L) { // 5-second timeout
            suspendCancellableCoroutine<Unit> { cont ->
                googleManager.createRewardedAd(
                    onLoading = {
                        Log.d("jejeshowRewardedAd", "Rewarded Ad is loading.")
                        callback.invoke(RewardedAdStatus.AdLoading)
                    },
                    onAdReady = { adMob ->
                        if (adMob != null) {
                            Log.d(
                                "jejeshowRewardedAd",
                                "Rewarded Ad loaded successfully, showing ad."
                            )
                            // Set up the ad callbacks
                            val listener = RewardedAdListener(callback, analytics, googleManager)
                            adMob.fullScreenContentCallback = listener
                            adMob.show(this@showRewardedAd, listener)
                            // Hide loading indicator
                            hideLoadingIndicator.invoke()
                            cont.resume(Unit) {}
                        } else {
                            Log.d("jejeshowRewardedAd", "Rewarded Ad failed to load.")
                            // Hide loading indicator and invoke callback
                            hideLoadingIndicator.invoke()
                            callback.invoke(RewardedAdStatus.AdNotAvailable)
                            cont.resume(Unit) {}
                        }
                    }
                )
            }
        }

        if (adLoaded == null) { // Timeout occurred
            Log.d("jejeshowRewardedAd", "Rewarded Ad loading timed out.")
            hideLoadingIndicator.invoke()
            callback.invoke(RewardedAdStatus.AdNotAvailable)
        }
    }
}
sealed class RewardedAdStatus {
    data object UserRewarded : RewardedAdStatus()
    data object AdNotAvailable : RewardedAdStatus()
    data object AdAvailable : RewardedAdStatus()
    data object UserCancelled : RewardedAdStatus()
    data object AdLoading : RewardedAdStatus()
}

class RewardedAdListener(
    private val callback: (RewardedAdStatus) -> Unit,
    private val analytics: Analytics,
    private val googleManager: GoogleManager // Inject GoogleManager
) : FullScreenContentCallback(), OnUserEarnedRewardListener {

    override fun onAdClicked() {
        super.onAdClicked()
        Log.d("jejeRewardedAdListener", "Ad clicked.")
        // Optionally, log ad clicks if required
    }

    override fun onAdDismissedFullScreenContent() {
        super.onAdDismissedFullScreenContent()
        Log.d("jejeRewardedAdListener", "Ad dismissed.")
        callback.invoke(RewardedAdStatus.UserCancelled)
        // Pre-load the next ad
        googleManager.createRewardedAd(
            onLoading = { /* Optionally, handle loading state */ },
            onAdReady = { adMob ->
                if (adMob != null) {
                    Log.d("RewardedAdListener", "Rewarded Ad pre-loaded successfully.")
                } else {
                    Log.d("RewardedAdListener", "Failed to pre-load Rewarded Ad.")
                }
            }
        )
    }

    override fun onAdImpression() {
        super.onAdImpression()
        Log.d("jejeRewardedAdListener", "Ad impression recorded.")
        // Optionally, log ad impressions if required
    }

    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
        super.onAdFailedToShowFullScreenContent(adError)
        Log.e("jejeRewardedAdListener", "Ad failed to show: ${adError.message}")
        callback.invoke(RewardedAdStatus.AdNotAvailable)
    }

    override fun onAdShowedFullScreenContent() {
        super.onAdShowedFullScreenContent()
        Log.d("jejeRewardedAdListener", "Ad showed successfully.")
        // No callback invocation here to prevent interference with timeout
    }

    override fun onUserEarnedReward(rewardItem: RewardItem) {
        Log.d("RewardedAdListener", "User earned reward: ${rewardItem.amount} ${rewardItem.type}")
        callback.invoke(RewardedAdStatus.UserRewarded)
        // Pre-load the next ad
        googleManager.createRewardedAd(
            onLoading = { /* Optionally, handle loading state */ },
            onAdReady = { adMob ->
                if (adMob != null) {
                    Log.d("RewardedAdListener", "Rewarded Ad pre-loaded successfully.")
                } else {
                    Log.d("RewardedAdListener", "Failed to pre-load Rewarded Ad.")
                }
            }
        )
    }
}

fun isConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        capabilities?.let {
            when {
                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } ?: false

    } else {
        val netInfo = connectivityManager.activeNetworkInfo
        netInfo != null && netInfo.isConnected
    }
}