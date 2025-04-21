package com.thezayin.paksimdetails.ui.result.presentation.di

import com.thezayin.paksimdetails.ui.history.domain.usecase.GetHistory
import com.thezayin.paksimdetails.ui.history.domain.usecase.GetHistoryImpl
import com.thezayin.paksimdetails.ui.result.data.network.ResultApi
import com.thezayin.paksimdetails.ui.result.data.repository.ResultRepositoryImpl
import com.thezayin.paksimdetails.ui.result.domain.repository.ResultRepository
import com.thezayin.paksimdetails.ui.result.domain.usecase.GetResult
import com.thezayin.paksimdetails.ui.result.domain.usecase.GetResultImpl
import com.thezayin.paksimdetails.ui.result.presentation.ResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val resultModule = module {
    singleOf(::ResultApi)
    viewModelOf(::ResultViewModel)
    singleOf(::GetResultImpl) bind GetResult::class
    singleOf(::ResultRepositoryImpl) bind ResultRepository::class
    singleOf(::GetHistoryImpl) bind GetHistory::class
}