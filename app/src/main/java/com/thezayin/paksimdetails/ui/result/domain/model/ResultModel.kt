package com.thezayin.paksimdetails.ui.result.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ResultModel(
    val number: String = "",
    var name: String = "",
    val cnic: String = "",
    val address: String = "",
)