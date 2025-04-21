package com.thezayin.paksimdetails.ui.setting.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.thezayin.paksimdetails.R
import com.thezayin.paksimdetails.ui.common.component.BannerAd
import com.thezayin.paksimdetails.ui.common.topbar.TopBar
import ir.kaaveh.sdpcompose.sdp

@Composable
fun SettingScreenContent(
    modifier: Modifier,
    onBackClick: () -> Unit,
    onPremiumClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = colorResource(id = R.color.background),
        topBar = {
            Column {
                TopBar(
                    modifier = Modifier,
                    onBackClick = onBackClick,
                    onPremiumClick = onPremiumClick,
                    screenName = stringResource(id = R.string.settings_screen_name)
                )
            }
        },
        bottomBar = {
            BannerAd()
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = 60.sdp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            SettingOptionsList()
        }
    }
}
