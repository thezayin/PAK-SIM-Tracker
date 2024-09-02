package com.thezayin.paksimdetails.navigation

import kotlinx.serialization.Serializable

@Serializable
object SplashScreenNav

@Serializable
object HomeScreenNav

@Serializable
object ServerScreenNav

@Serializable
data class WebScreenNav(val url: String)

@Serializable
object SettingScreenNav

@Serializable
object PremiumScreenNav