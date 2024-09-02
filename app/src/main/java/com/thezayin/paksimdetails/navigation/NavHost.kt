package com.thezayin.paksimdetails.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.thezayin.presentation.HomeScreen
import com.thezayin.premium.PremiumScreen
import com.thezayin.presentation.ServerScreen
import com.thezayin.setting.SettingScreen
import com.thezayin.splash.SplashScreen
import com.thezayin.web.WebScreen

@Composable
fun NavHost(navController: NavHostController) {
    androidx.navigation.compose.NavHost(
        navController = navController, startDestination = SplashScreenNav
    ) {
        composable<SplashScreenNav> {
            SplashScreen(onNavigate = {
                navController.navigate(HomeScreenNav)
            })
        }

        composable<HomeScreenNav> {
            HomeScreen(onMenuClick = {
                navController.navigate(SettingScreenNav)
            }, onPremiumClick = {
                navController.navigate(PremiumScreenNav)
            }, onServerClick = {
                navController.navigate(ServerScreenNav)
            })
        }

        composable<ServerScreenNav> {
            ServerScreen(onServerClick = {
                navController.navigate(WebScreenNav(it))
            }, onBackPress = { navController.navigateUp() }, onPremiumClick = {
                navController.navigate(PremiumScreenNav)
            })
        }

        composable<WebScreenNav> {
            val args = it.toRoute<WebScreenNav>()
            WebScreen(url = args.url,
                onBackPress = { navController.navigateUp() },
                onPremiumClick = {
                    navController.navigate(PremiumScreenNav)
                })
        }

        composable<SettingScreenNav> {
            SettingScreen(onBackClick = { navController.navigateUp() }, onPremiumClick = {
                navController.navigate(PremiumScreenNav)
            })
        }

        composable<PremiumScreenNav> {
            PremiumScreen(onBackClick = { navController.navigateUp() })
        }
    }
}