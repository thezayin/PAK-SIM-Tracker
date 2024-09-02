package com.thezayin.framework.remote

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.json.Json
import timber.log.Timber

private const val INIT_ADS = "init_ads"
private const val SERVER_LIST = "server_list"
private const val AD_CONFIGS = "ad_configs"

class RemoteConfig(
    private val json: Json
) {
    private val default: Map<String, Any> = mapOf(
        INIT_ADS to true,
        SERVER_LIST to defaultServerUrl,
        AD_CONFIGS to defaultAdConfigs
    )

    @SuppressLint("LogNotTimber")
    private val config = FirebaseRemoteConfig.getInstance().apply {
        setConfigSettingsAsync(remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        })
        setDefaultsAsync(default)
        fetchAndActivate().addOnCompleteListener {
            Log.d("RemoteConfig", "fetchAndActivate: ${all.mapValues { (_, v) -> v.asString() }}")
        }
    }

    val initAds: Boolean
        get() = config.getBoolean(INIT_ADS)

    val serverList: ServerUrl
        get() = try {
            val serverListJson = config.getString(SERVER_LIST)
            Log.d("RemoteConfig", "ServerList JSON: $serverListJson")
            json.decodeFromString(ServerUrl.serializer(), serverListJson)
        } catch (e: Exception) {
            Log.e("RemoteConfig", "Error decoding ServerList JSON", e)
            json.decodeFromString(ServerUrl.serializer(), defaultServerUrl)  // Fallback to default
        }

    @OptIn(ExperimentalSerializationApi::class)
    val adConfigs: AdConfigs
        get() = try {
            val adConfigsJson = config.getString(AD_CONFIGS)
            Timber.tag("RemoteConfig").d("AdConfigs JSON: %s", adConfigsJson)
            if (adConfigsJson.isBlank()) {
                Timber.tag("RemoteConfig").e("Received empty or null JSON for AdConfigs")
                AdConfigs()  // Return default AdConfigs if the JSON is empty or null
            } else {
                json.decodeFromString(AdConfigs.serializer(), adConfigsJson)
            }
        } catch (e: MissingFieldException) {
            Timber.tag("RemoteConfig").e(e, "Missing fields in AdConfigs JSON")
            AdConfigs()  // Return default AdConfigs in case of MissingFieldException
        } catch (e: Exception) {
            Timber.tag("RemoteConfig").e(e, "Error decoding AdConfigs JSON")
            AdConfigs()  // Handle any other exceptions and provide fallback
        }
}
