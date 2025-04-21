package com.thezayin.paksimdetails.ui.server.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.thezayin.paksimdetails.R
import com.thezayin.paksimdetails.ui.common.component.BannerAd
import com.thezayin.paksimdetails.ui.common.topbar.TopBar
import com.thezayin.paksimdetails.ui.server.domain.model.ServerModel
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ServerScreenContent(
    modifier: Modifier,
    onBackClick: () -> Unit,
    onPremiumClick: () -> Unit,
    onServerClick: (String) -> Unit,
    list: List<ServerModel>,
) {
    Scaffold(
        modifier = modifier
            .navigationBarsPadding()
            .statusBarsPadding()
            .fillMaxSize(),
        containerColor = colorResource(id = R.color.background),
        topBar = {
            TopBar(
                modifier = Modifier,
                onBackClick = onBackClick,
                onPremiumClick = onPremiumClick,
                screenName = stringResource(id = R.string.servers_screen_name)
            )
        },
        bottomBar = {
            BannerAd()
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(vertical = 10.sdp)
        ) {
            ServerMenu(
                modifier = modifier, list = list, onServerClick = onServerClick
            )
        }
    }
}
