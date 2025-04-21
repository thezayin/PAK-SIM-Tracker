package com.thezayin.paksimdetails.ui.history.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "search_history_table")
@Serializable
data class HistoryModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val searchSuccess: Boolean,
    val phoneNumber: String,
    val date: String,
    val name: String? = "",
    val cnic: String? = "",
    val address: String? = "",
)