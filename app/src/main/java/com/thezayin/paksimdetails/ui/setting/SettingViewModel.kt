package com.thezayin.paksimdetails.ui.setting

import androidx.lifecycle.ViewModel
import com.thezayin.paksimdetails.framework.admob.domain.repository.InterstitialAdManager
import com.thezayin.paksimdetails.framework.analytics.analytics.Analytics
import com.thezayin.paksimdetails.framework.remote.RemoteConfig

class SettingViewModel(
    val remoteConfig: RemoteConfig,
    val analytics: Analytics,
    val adManager: InterstitialAdManager
) : ViewModel()