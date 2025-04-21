package com.thezayin.paksimdetails.ui.history.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thezayin.paksimdetails.ui.history.data.dao.HistoryDao
import com.thezayin.paksimdetails.ui.history.domain.model.HistoryModel

/**
 * Room database class for the application.
 */
@Database(
    entities = [HistoryModel::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}