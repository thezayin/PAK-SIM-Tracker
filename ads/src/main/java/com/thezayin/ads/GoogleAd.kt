package com.thezayin.ads

import android.util.Log
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.LoadAdError

sealed interface AdStatus<out T> {
    data object Loading : AdStatus<Nothing>
    data class Loaded<T>(val data: T) : AdStatus<T>
    data class Error(val error: LoadAdError) : AdStatus<Nothing>
}

abstract class AdBuilder<T> : ((AdStatus<T>) -> Unit) -> Unit {
    protected var onPaid: ((AdValue) -> Unit)? = null
    abstract val platform: String

    fun onPaid(onPaid: ((AdValue) -> Unit)) {
        this.onPaid = onPaid
    }
}

class GoogleAd<T>(private val builder: AdBuilder<T>) {
    private var adInstance: AdStatus<T> = AdStatus.Loading
    private var isLoading = false

    /**
     * Initiates the ad loading process.
     *
     * @param onLoading Callback invoked when the ad starts loading.
     * @param onAdReady Callback invoked when the ad is ready or unavailable.
     */
    fun get(onLoading: () -> Unit, onAdReady: (T?) -> Unit) {
        when (val instance = adInstance) {
            is AdStatus.Loaded -> {
                Log.d("jejeGoogleAd", "Ad loaded")
                onAdReady(instance.data)
                // Do not reload the ad here to prevent multiple loads
            }
            is AdStatus.Error -> {
                Log.e("jejeGoogleAd", "Failed to load ad: ${instance.error}")
                onLoading()
                loadAd()
                onAdReady(null)
            }
            is AdStatus.Loading -> {
                Log.d("jejeGoogleAd", "Loading ad...")
                onLoading()
                loadAd() // Initiate ad loading
            }
        }
    }

    /**
     * Starts loading the ad using the builder.
     */
    fun loadAd() {
        if (isLoading) return
        isLoading = true
        adInstance = AdStatus.Loading
        Log.d("jejeGoogleAd", "Initiating ad load...")

        builder { adStatus ->
            Log.d("jejeGoogleAd", "Ad loaded $adStatus")
            adInstance = adStatus
            isLoading = false
        }
    }
}
