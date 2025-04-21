package com.thezayin.paksimdetails.ui.result.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.thezayin.paksimdetails.ui.result.domain.model.ResultModel
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ResultScreenContent(
    modifier: Modifier,
    result: ResultModel?,
    resultNotFound: Boolean?,
    onBackClick: () -> Unit,
    onPremiumClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier
            .navigationBarsPadding()
            .statusBarsPadding()
            .fillMaxSize(),
        containerColor = colorResource(id = R.color.background),
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                TopBar(
                    modifier = Modifier,
                    onBackClick = onBackClick,
                    onPremiumClick = onPremiumClick,
                    screenName = stringResource(id = R.string.result_screen_name) // Use string resource here
                )
            }
        },
        bottomBar = {
            BannerAd()
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .fillMaxSize()
                .padding(vertical = 35.sdp, horizontal = 10.sdp)
        ) {
            result?.let { result ->
                ResultContent(
                    modifier = Modifier,
                    name = result.name,
                    cnic = result.cnic,
                    address = result.address,
                    number = result.number
                )
            }
            resultNotFound?.let {
                ResultNotFound(modifier = Modifier)
            }
        }
    }
}