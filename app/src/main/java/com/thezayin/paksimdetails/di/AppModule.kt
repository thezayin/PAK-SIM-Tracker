package com.thezayin.paksimdetails.di

import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.paksimdetails.domain.repository.SimRepository
import com.thezayin.paksimdetails.domain.usecase.RemoteUseCase
import com.thezayin.paksimdetails.domain.usecase.RemoteUseCaseImpl
import com.thezayin.paksimdetails.data.remote.network.ApiService
import com.thezayin.paksimdetails.data.remote.repository.SimRepositoryImpl
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.thezayin.paksimdetails.presentation.home.HomeViewModel
import com.thezayin.paksimdetails.presentation.activities.MainViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::ApiService)
    single { Json { ignoreUnknownKeys = true } }
    singleOf(::RemoteConfig)
    factoryOf(::SimRepositoryImpl) bind SimRepository::class
    factoryOf(::RemoteUseCaseImpl) bind RemoteUseCase::class
    viewModelOf(::HomeViewModel)
    viewModelOf(::MainViewModel)
}