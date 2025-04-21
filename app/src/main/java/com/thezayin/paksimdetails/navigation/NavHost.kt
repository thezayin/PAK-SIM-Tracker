package com.thezayin.paksimdetails.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.thezayin.paksimdetails.ui.history.presentation.HistoryScreen
import com.thezayin.paksimdetails.ui.home.presentation.HomeScreen
import com.thezayin.paksimdetails.ui.language.presentation.LanguageScreen
import com.thezayin.paksimdetails.ui.onboarding.OnboardingScreen
import com.thezayin.paksimdetails.ui.premium.PremiumScreen
import com.thezayin.paksimdetails.ui.result.presentation.ResultScreen
import com.thezayin.paksimdetails.ui.server.presentation.ServerScreen
import com.thezayin.paksimdetails.ui.setting.SettingScreen
import com.thezayin.paksimdetails.ui.splash.SplashScreen
import com.thezayin.paksimdetails.ui.web.WebScreen

@Composable
fun NavHost(navController: NavHostController) {
    androidx.navigation.compose.NavHost(
        navController = navController, startDestination = SplashScreenNav
    ) {
        composable<HistoryScreenNav> {
            HistoryScreen(onBackPress = { navController.navigateUp() })
        }

        composable<LanguageScreenNav> {
            LanguageScreen(
                navigateToOnboarding = {
                    navController.navigate(OnboardingScreenNav) {
                        popUpTo(SplashScreenNav) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable<SplashScreenNav> {
            SplashScreen(
                navigateToLanguageScreen = {
                    navController.navigate(LanguageScreenNav) {
                        popUpTo(SplashScreenNav) {
                            inclusive = true
                        }
                    }
                },
                navigateToHome = {
                    navController.navigate(HomeScreenNav)
                }
            )
        }

        composable<OnboardingScreenNav> {
            OnboardingScreen(navigateToHome = {
                navController.navigate(HomeScreenNav)
            })
        }

        composable<HomeScreenNav> {
            HomeScreen(
                onMenuClick = {
                    navController.navigate(SettingScreenNav)
                }, onHistoryClick = {
                    navController.navigate(HistoryScreenNav)
                }, onServerClick = {
                    navController.navigate(ServerScreenNav)
                },
                onSearchClick = { number ->
                    navController.navigate(ResultScreenNav(number))
                }
            )
        }

        composable<ResultScreenNav> {
            val args = it.toRoute<ResultScreenNav>()
            ResultScreen(
                phoneNumber = args.phoneNumber,
                onBackPress = { navController.navigateUp() },
                onPremiumClick = {
                    navController.navigate(PremiumScreenNav)
                }
            )
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
            WebScreen(
                url = args.url,
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