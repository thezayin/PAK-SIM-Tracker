package com.thezayin.paksimdetails.ui.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.thezayin.paksimdetails.framework.admob.data.repository.AppOpenAdManagerImpl
import com.thezayin.paksimdetails.framework.admob.data.repository.InterstitialAdManagerImpl
import com.thezayin.paksimdetails.framework.admob.data.repository.RewardedAdManagerImpl
import com.thezayin.paksimdetails.framework.admob.domain.repository.AppOpenAdManager
import com.thezayin.paksimdetails.framework.admob.domain.repository.InterstitialAdManager
import com.thezayin.paksimdetails.framework.admob.domain.repository.RewardedAdManager
import com.thezayin.paksimdetails.framework.analytics.analytics.Analytics
import com.thezayin.paksimdetails.framework.analytics.analytics.AnalyticsImpl
import com.thezayin.paksimdetails.framework.pref.PreferencesManager
import com.thezayin.paksimdetails.framework.remote.RemoteConfig
import com.thezayin.paksimdetails.ui.history.data.di.provideDatabase
import com.thezayin.paksimdetails.ui.history.data.di.provideHistoryDao
import com.thezayin.paksimdetails.ui.history.data.repository.HistoryRepositoryImpl
import com.thezayin.paksimdetails.ui.history.domain.repository.HistoryRepository
import com.thezayin.paksimdetails.ui.history.domain.usecase.AddHistory
import com.thezayin.paksimdetails.ui.history.domain.usecase.AddHistoryImpl
import com.thezayin.paksimdetails.ui.history.domain.usecase.ClearHistoryUseCase
import com.thezayin.paksimdetails.ui.history.domain.usecase.ClearHistoryUseCaseImpl
import com.thezayin.paksimdetails.ui.history.domain.usecase.GetHistory
import com.thezayin.paksimdetails.ui.history.domain.usecase.GetHistoryImpl
import com.thezayin.paksimdetails.ui.history.presentation.HistoryViewModel
import com.thezayin.paksimdetails.ui.home.presentation.HomeViewModel
import com.thezayin.paksimdetails.ui.language.data.repository.LanguageRepositoryImpl
import com.thezayin.paksimdetails.ui.language.domain.repository.LanguageRepository
import com.thezayin.paksimdetails.ui.language.domain.usecase.GetSelectedLanguageUseCase
import com.thezayin.paksimdetails.ui.language.domain.usecase.GetSelectedLanguageUseCaseImpl
import com.thezayin.paksimdetails.ui.language.domain.usecase.SetLanguageUseCase
import com.thezayin.paksimdetails.ui.language.domain.usecase.SetLanguageUseCaseImpl
import com.thezayin.paksimdetails.ui.language.presentation.LanguageViewModel
import com.thezayin.paksimdetails.ui.onboarding.OnboardingViewModel
import com.thezayin.paksimdetails.ui.premium.PremiumViewModel
import com.thezayin.paksimdetails.ui.server.data.ServerRepositoryImpl
import com.thezayin.paksimdetails.ui.server.domain.repository.ServerRepository
import com.thezayin.paksimdetails.ui.server.domain.usecase.ServerList
import com.thezayin.paksimdetails.ui.server.domain.usecase.ServerListImpl
import com.thezayin.paksimdetails.ui.server.presentation.ServerViewModel
import com.thezayin.paksimdetails.ui.setting.SettingViewModel
import com.thezayin.paksimdetails.ui.splash.SplashViewModel
import com.thezayin.paksimdetails.ui.web.WebViewModel
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { PreferencesManager(get()) }
    single { Json { ignoreUnknownKeys = true } }
    single { RemoteConfig(get()) }
    single<LanguageRepository> { LanguageRepositoryImpl(preferencesManager = get()) }
    single<PreferencesManager> { PreferencesManager(get()) }
    single<LanguageRepository> { LanguageRepositoryImpl(get()) }
    single<GetSelectedLanguageUseCase> { GetSelectedLanguageUseCaseImpl(get()) }
    single<SetLanguageUseCase> { SetLanguageUseCaseImpl(get()) }
    viewModelOf(::LanguageViewModel)
}

val analyticsModule = module {
    single { FirebaseAnalytics.getInstance(get()) }
    factoryOf(::AnalyticsImpl) bind Analytics::class
}

val splashModule = module {
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::SplashViewModel)
}

val historyModule = module {
    single { provideDatabase(get()) }
    single { provideHistoryDao(get()) }
    singleOf(::HistoryRepositoryImpl) bind HistoryRepository::class
    singleOf(::AddHistoryImpl) bind AddHistory::class
    viewModelOf(::HistoryViewModel)
    singleOf(::ClearHistoryUseCaseImpl) bind ClearHistoryUseCase::class
    singleOf(::GetHistoryImpl) bind GetHistory::class
}

val homeModule = module {
    viewModelOf(::HomeViewModel)
}

val serverModule = module {
    viewModelOf(::ServerViewModel)
    singleOf(::ServerListImpl) bind ServerList::class
    singleOf(::ServerRepositoryImpl) bind ServerRepository::class
}

val webModule = module {
    viewModelOf(::WebViewModel)
}

val settingModule = module {
    viewModelOf(::SettingViewModel)
    single<InterstitialAdManager> { InterstitialAdManagerImpl() }
    single<AppOpenAdManager> { AppOpenAdManagerImpl() }
    single<RewardedAdManager> { RewardedAdManagerImpl() }
}

val premiumModule = module {
    viewModelOf(::PremiumViewModel)
}