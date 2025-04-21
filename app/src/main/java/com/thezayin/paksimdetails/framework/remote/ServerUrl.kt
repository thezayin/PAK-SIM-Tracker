package com.thezayin.paksimdetails.framework.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerUrl(
    @SerialName("first_url") val first: String,
    @SerialName("second_url") val second: String,
    @SerialName("third_url") val third: String,
    @SerialName("fourth_url") val fourth: String,
    @SerialName("fifth_url") val fifth: String
)

val defaultServerUrl = """
{
  "first_url": "https://simownerdetails.net.pk/",
  "second_url": "https://simdetail.pro/",
  "third_url": "https://simownerdetails.net.pk/pak-sim-data/",
  "fourth_url": "https://freshsimdatabase.com/",
  "fifth_url": "https://checksimowner.com/pak-sim-data/"
}
""".trimIndent()