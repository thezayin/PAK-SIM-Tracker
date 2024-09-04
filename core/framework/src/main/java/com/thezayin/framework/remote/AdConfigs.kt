package com.thezayin.framework.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdConfigs(
    @SerialName("init_ads") val initAds: Boolean = false,
    @SerialName("appOpenAd") val appOpenAd: Boolean = false,
    @SerialName("adOnSplashScreen") val adOnSplashScreen: Boolean = false,
    @SerialName("adOnPremiumClick") val adOnPremiumClick: Boolean = false,
    @SerialName("adOnSettingClick") val adOnSettingClick: Boolean = false,
    @SerialName("adOnServerClick") val adOnServerClick: Boolean = false,
    @SerialName("adOnSearchClick") val adOnSearchClick: Boolean = false,
    @SerialName("adOnBackPress") val adOnBackPress: Boolean = false,
    @SerialName("onServerClick") val onServerClick: Boolean = false,
    @SerialName("nativeAdOnHomeScreen") val nativeAdOnHomeScreen: Boolean = false,
    @SerialName("nativeAdOnResultScreen") val nativeAdOnResultScreen: Boolean = false,
    @SerialName("nativeAdOnResultLoadingDialog") val nativeAdOnResultLoadingDialog: Boolean = false,
    @SerialName("nativeAdOnSettingScreen") val nativeAdOnSettingScreen: Boolean = false,
    @SerialName("nativeAdOnServerScreen") val nativeAdOnServerScreen: Boolean = false,
    @SerialName("nativeAdOnServerLoadingDialog") val nativeAdOnServerLoadingDialog: Boolean = false,
    @SerialName("nativeAdOnWebScreen") val nativeAdOnWebScreen: Boolean = false,
    @SerialName("nativeAdOnWebLoadingDialog") val nativeAdOnWebLoadingDialog: Boolean = false,
    @SerialName("nativeAdOnPremiumScreen") val nativeAdOnPremiumScreen: Boolean = false
)

val defaultAdConfigs = """
   {
   "init_ads": false,
   "appOpenAd": false,
   "adOnSplashScreen": false,
   "adOnPremiumClick": false,
   "adOnSettingClick": false,
   "adOnServerClick": false,
   "adOnSearchClick": false,
   "adOnBackPress": false,
   "onServerClick": false,
   "nativeAdOnHomeScreen": false,
   "nativeAdOnResultScreen": false,
   "nativeAdOnResultLoadingDialog": false,
   "nativeAdOnSettingScreen": false,
   "nativeAdOnServerScreen": false,
   "nativeAdOnServerLoadingDialog": false,
   "nativeAdOnWebScreen": false,
   "nativeAdOnWebLoadingDialog": false,
   "nativeAdOnPremiumScreen": false
}
""".trimIndent()