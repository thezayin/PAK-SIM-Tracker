package com.thezayin.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thezayin.data.dao.HistoryDao
import com.thezayin.domain.model.HistoryModel

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