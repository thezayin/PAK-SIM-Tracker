package com.thezayin.paksimdetails.ui.result.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

class ResultApi {
    companion object {
        private const val TIME_MILLS = 60000L
        private const val BASE_URL = "https://sim-ownership.com/search.php"
    }

    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

    private val client = HttpClient(Android) {
        install(HttpTimeout) {
            socketTimeoutMillis = TIME_MILLS
            requestTimeoutMillis = TIME_MILLS
            connectTimeoutMillis = TIME_MILLS
        }

        install(ContentNegotiation) { json(json) }

        install(Logging) {
            logger = object : io.ktor.client.plugins.logging.Logger {
                override fun log(message: String) {
                    Timber.tag("API Response").i(message)
                }
            }
            level = LogLevel.ALL
        }
    }

    suspend fun searchNumber(number: String): String {
        return client.post {
            url(BASE_URL)
            parameter("type", "mobile")
            parameter("search", number)
        }.body()
    }
}