package com.thezayin.data.network

import com.thezayin.framework.utils.Constants.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

class ResultApi {
    companion object {
        private const val TIME_MILLS = 60000L
        private const val TIME_OUT = 60_000
        private const val TAG_KTOR_LOGGER = "KtorLogger"
        private const val TAG_KTOR_HTTPS_STATUS_LOGGER = "httpsStatusLogger"
    }

    private val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    private val client = HttpClient(Android) {
        install(HttpTimeout) {
            socketTimeoutMillis = TIME_MILLS
            requestTimeoutMillis = TIME_MILLS
            connectTimeoutMillis = TIME_MILLS
        }

        install(DefaultRequest) {
            headers.append("Accept", "application/json")
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.tag(TAG_KTOR_LOGGER).i(message)
                }
            }
        }

        install(ContentNegotiation) {
            json(json)
        }

        install(ResponseObserver) {
            onResponse { response ->
                Timber.tag(TAG_KTOR_HTTPS_STATUS_LOGGER).i("Response: %s", response.status.value)
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        engine {
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    suspend fun searchNumber(number: String): String {
        return client.get {
            url(BASE_URL)
            parameter("type", "mobile")
            parameter("search", number)
        }.body()
    }
}