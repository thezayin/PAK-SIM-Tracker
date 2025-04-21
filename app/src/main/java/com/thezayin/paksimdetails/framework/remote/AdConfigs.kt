package com.thezayin.paksimdetails.framework.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdConfigs(
    @SerialName("adOnSplashScreen") val adOnSplashScreen: Boolean = true,
    @SerialName("bannerAd") val bannerAd: Boolean = true,
    @SerialName("resumeAppOpenAd") val resumeAppOpenAd: Boolean = true,
    @SerialName("adOnOnboardingCompleted") val adOnOnboardingCompleted: Boolean = true,
    @SerialName("adOnHistoryClick") val adOnHistoryClick: Boolean = true,
    @SerialName("adOnServerOptionClick") val adOnServerOptionClick: Boolean = true,
    @SerialName("adOnSearchClick") val adOnSearchClick: Boolean = true,
    @SerialName("adOnDeleteClick") val adOnDeleteClick: Boolean = true,
    @SerialName("onServerClick") val onServerClick: Boolean = true,
)

val defaultAdConfigs = """
{
  "adOnSplashScreen": true,
  "bannerAd": true,
  "adOnOnboardingCompleted": true,
  "adOnHistoryClick": true,
  "adOnServerOptionClick": true,
  "adOnSearchClick": true,
  "adOnDeleteClick": true,
  "onServerClick": true
}
""".trimIndent()