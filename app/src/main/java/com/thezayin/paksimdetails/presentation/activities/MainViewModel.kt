package com.thezayin.paksimdetails.presentation.activities

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.GoogleManager
import com.thezayin.framework.remote.RemoteConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("NewApi")
class MainViewModel(
    val googleManager: GoogleManager,
    val remoteConfig: RemoteConfig
) : ViewModel() {

    var nativeAd = mutableStateOf<NativeAd?>(null)
        private set

    fun getNativeAd() = viewModelScope.launch {
        nativeAd.value = googleManager.createNativeAd() ?: run {
            delay(3000)
            Timber.d("MainViewModel", "getNativeAd:${nativeAd} ")
            googleManager.createNativeAd()
        }
    }
}