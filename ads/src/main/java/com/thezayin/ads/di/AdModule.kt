package com.thezayin.ads.di

import com.thezayin.ads.GoogleManager
import com.thezayin.ads.ump.ConsentManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val adModule = module {
    single { ConsentManager(androidContext()) }
    single { GoogleManager(androidContext(), get()) }
}