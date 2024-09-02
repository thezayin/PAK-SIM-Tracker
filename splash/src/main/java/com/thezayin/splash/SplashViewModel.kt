package com.thezayin.splash

import androidx.lifecycle.ViewModel
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.framework.remote.RemoteConfig

class SplashViewModel(
    val googleManager: GoogleManager,
    val remoteConfig: RemoteConfig,
    val analytics: Analytics
) :
    ViewModel()