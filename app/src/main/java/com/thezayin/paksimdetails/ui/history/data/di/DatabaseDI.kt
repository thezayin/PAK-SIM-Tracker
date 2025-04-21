package com.thezayin.paksimdetails.ui.history.data.di

import android.content.Context
import androidx.room.Room
import com.thezayin.paksimdetails.ui.history.data.db.AppDatabase

fun provideDatabase(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration(false)
        .build()

fun provideHistoryDao(database: AppDatabase) = database.historyDao()

