package com.thezayin.paksimdetails.ui.home.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import com.thezayin.paksimdetails.R
import com.thezayin.paksimdetails.ui.common.component.BannerAd

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    onHistoryClick: () -> Unit,
    onMenuClick: () -> Unit,
    onServerClick: () -> Unit,
    onSearchClick: (String) -> Unit,
) {
    val number = remember { mutableStateOf(TextFieldValue()) }
    val showWarning = remember { mutableStateOf(false) }
    Scaffold(
        modifier = modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        containerColor = colorResource(id = R.color.background),
        topBar = {
            Column {
                HomeTopBar(
                    modifier = Modifier, onMenuClick = onMenuClick, onHistoryClick = onHistoryClick
                )
            }
        },
        bottomBar = {
            BannerAd()
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            SearchCard(
                modifier = Modifier,
                number = number,
                showWarning = showWarning,
                onSearchClick = onSearchClick
            )
            TipsAndTricksSection()
            ServerBar(onServerClick = onServerClick)
        }
    }
}