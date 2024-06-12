package com.thezayin.paksimdetails.presentation.servers.di

import com.thezayin.paksimdetails.presentation.servers.data.repository.ServerRepositoryImpl
import com.thezayin.paksimdetails.presentation.servers.domain.repository.ServerRepository
import com.thezayin.paksimdetails.presentation.servers.domain.usecase.ServerUseCase
import com.thezayin.paksimdetails.presentation.servers.domain.usecase.ServerUseCaseImpl
import com.thezayin.paksimdetails.presentation.servers.presentation.ServerViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val serverModule = module {
    viewModelOf(::ServerViewModel)

    factoryOf(::ServerRepositoryImpl) bind ServerRepository::class
    factoryOf(::ServerUseCaseImpl) bind ServerUseCase::class
}