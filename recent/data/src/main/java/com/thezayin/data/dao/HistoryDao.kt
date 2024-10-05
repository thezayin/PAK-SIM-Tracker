package com.thezayin.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thezayin.domain.model.HistoryModel

@Dao
interface HistoryDao {

    /**
     * Retrieves all search history entries, ordered by ID in ascending order.
     */
    @Query("SELECT * FROM search_history_table ORDER BY id ASC")
    fun getAllHistory(): List<HistoryModel>

    /**
     * Deletes all entries from the search history table.
     */
    @Query("DELETE FROM search_history_table")
    suspend fun clearHistory()

    /**
     * Inserts a new history entry. Ignores the insert if there's a conflict.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(history: HistoryModel): Long
}