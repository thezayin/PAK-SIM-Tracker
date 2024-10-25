package com.thezayin.paksimdetails.game.presentation.di

import android.content.SharedPreferences
import com.thezayin.paksimdetails.game.data.local.MemoryGameLocalDataSource
import com.thezayin.paksimdetails.game.data.local.MemoryGameLocalDataSourceImpl
import com.thezayin.paksimdetails.game.data.repository.MemoryGameRepositoryImpl
import com.thezayin.paksimdetails.game.domain.repository.MemoryGameRepository
import com.thezayin.paksimdetails.game.domain.usecase.CheckSequenceUseCase
import com.thezayin.paksimdetails.game.domain.usecase.CheckSequenceUseCaseImpl
import com.thezayin.paksimdetails.game.domain.usecase.GenerateSequenceUseCase
import com.thezayin.paksimdetails.game.domain.usecase.GenerateSequenceUseCaseImpl
import com.thezayin.paksimdetails.game.domain.usecase.GetHighscoreUseCase
import com.thezayin.paksimdetails.game.domain.usecase.GetHighscoreUseCaseImpl
import com.thezayin.paksimdetails.game.presentation.MemoryGameViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gameModule = module {
    // Provide SharedPreferences
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "MemoryGamePrefs",
            android.content.Context.MODE_PRIVATE
        )
    }

    // Local Data Source
    single<MemoryGameLocalDataSource> {
        MemoryGameLocalDataSourceImpl(get())
    }

    // Repository
    single<MemoryGameRepository> {
        MemoryGameRepositoryImpl(get())
    }

    // Use Cases
    single<GenerateSequenceUseCase> {
        GenerateSequenceUseCaseImpl(get())
    }

    single<CheckSequenceUseCase> {
        CheckSequenceUseCaseImpl(get())
    }

    single<GetHighscoreUseCase> {
        GetHighscoreUseCaseImpl(get())
    }

    // ViewModel
    viewModel {
        MemoryGameViewModel(
            generateSequenceUseCase = get(),
            checkSequenceUseCase = get(),
            getHighscoreUseCase = get()
        )
    }
}