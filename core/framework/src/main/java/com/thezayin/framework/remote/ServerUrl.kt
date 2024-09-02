package com.thezayin.framework.remote

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
    "first_url": "https://paksimtracker.com/pak-sim-tracker/",
    "second_url": "https://simownerdata.pk/",
    "third_url": "https://simdatabase.info/search",
    "fourth_url": "https://pakdb.xyz/sim-ownership/",
    "fifth_url": "https://pakdataga.com/sim/search.php"
}
""".trimIndent()