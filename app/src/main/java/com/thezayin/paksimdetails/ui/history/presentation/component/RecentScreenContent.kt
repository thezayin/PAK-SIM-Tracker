package com.thezayin.paksimdetails.ui.history.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.thezayin.paksimdetails.R
import com.thezayin.paksimdetails.ui.common.component.BannerAd
import com.thezayin.paksimdetails.ui.history.domain.model.HistoryModel

@Composable
fun RecentScreenContent(
    modifier: Modifier = Modifier,
    list: List<HistoryModel>?,
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    historyNotFound: Boolean?,
) {
    Scaffold(
        modifier = modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        containerColor = colorResource(id = R.color.background),
        bottomBar = {
            BannerAd()
        },
        topBar = {
            Column {
                RecentTopBar(
                    modifier = Modifier,
                    onBackClick = onBackClick,
                    onDeleteClick = onDeleteClick
                )
            }
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (historyNotFound == null) {
                EmptyHistory(modifier = Modifier)
            }

            if (historyNotFound == false) {
                HistoryList(
                    modifier = Modifier, list = list
                )
            }
        }
    }
}