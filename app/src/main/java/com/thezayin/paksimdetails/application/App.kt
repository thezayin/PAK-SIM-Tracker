package com.thezayin.paksimdetails.application

import android.app.Application
import com.thezayin.di.adModule
import com.thezayin.di.analyticsModule
import com.thezayin.di.appModule
import com.thezayin.di.homeModule
import com.thezayin.di.premiumModule
import com.thezayin.di.serverModule
import com.thezayin.di.settingModule
import com.thezayin.di.splashModule
import com.thezayin.di.webModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                adModule,
                webModule,
                appModule,
                homeModule,
                splashModule,
                serverModule,
                settingModule,
                premiumModule,
                analyticsModule
            )
        }
    }
}