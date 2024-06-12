package com.thezayin.paksimdetails.presentation.activities.dialogs

import android.app.Activity
import com.thezayin.framework.extension.ads.showInterstitialAd
import com.thezayin.paksimdetails.presentation.activities.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Activity.interstitialAd(
    scope: CoroutineScope,
    viewModel: MainViewModel,
    showAd: Boolean,
    callBack: () -> Unit = {},

    ) {
    scope.launch(Dispatchers.Main) {
        showInterstitialAd(
            activity = this@interstitialAd,
            boolean = showAd,
            manager = viewModel.googleManager,
        ) {
            callBack()
        }
    }
}