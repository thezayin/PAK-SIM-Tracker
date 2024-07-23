package com.thezayin.ads

import com.google.android.gms.ads.LoadAdError

sealed interface AdStatus<out T> {
    data object Loading : AdStatus<Nothing>
    data class Loaded<T>(val data: T) : AdStatus<T>
    data class Error(val error: LoadAdError) : AdStatus<Nothing>
}

fun interface AdBuilder<T> : ((AdStatus<T>) -> Unit) -> Unit

class GoogleAd<T>(val builder: AdBuilder<T>) {
    private var ads: MutableList<AdStatus<T>> = mutableListOf(
        AdStatus.Loading,
        AdStatus.Loading
    )

    init {
        for (ad in ads) builder {
            ads.remove(ad)
            ads.add(it)
        }
    }

    fun get(): T? {
        // Handle any ad that has failed to load
        for (err in ads.filterIsInstance<AdStatus.Error>()) {
            ads.remove(err)
            builder {
                ads.add(it)
            }
        }

        // Return the first loaded ad
        val instance = ads
            .filterIsInstance<AdStatus.Loaded<T>>()
            .firstOrNull()

        // Remove the ad from the list and create a new one
        if (instance != null) {
            ads.remove(instance)
            builder {
                ads.add(it)
            }
        }

        return instance?.data

    }

}