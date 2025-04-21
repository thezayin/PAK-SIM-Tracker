package com.thezayin.paksimdetails.application

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.thezayin.paksimdetails.ui.di.analyticsModule
import com.thezayin.paksimdetails.ui.di.appModule
import com.thezayin.paksimdetails.ui.di.historyModule
import com.thezayin.paksimdetails.ui.di.homeModule
import com.thezayin.paksimdetails.ui.di.premiumModule
import com.thezayin.paksimdetails.ui.di.serverModule
import com.thezayin.paksimdetails.ui.di.settingModule
import com.thezayin.paksimdetails.ui.di.splashModule
import com.thezayin.paksimdetails.ui.di.webModule
import com.thezayin.paksimdetails.ui.result.presentation.di.resultModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) { initializationStatus ->
            Timber.d("AdMob Initialization: ${initializationStatus.adapterStatusMap}")
        }
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                resultModule,
                webModule,
                appModule,
                homeModule,
                splashModule,
                serverModule,
                settingModule,
                premiumModule,
                analyticsModule,
                historyModule,
            )
        }
    }
}