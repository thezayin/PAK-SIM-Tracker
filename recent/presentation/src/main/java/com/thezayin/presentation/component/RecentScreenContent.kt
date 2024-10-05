package com.thezayin.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.common.component.GlassComponent
import com.thezayin.domain.model.HistoryModel
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun RecentScreenContent(
    modifier: Modifier = Modifier,
    showNativeAd: Boolean,
    nativeAd: NativeAd?,
    list: List<HistoryModel>?,
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    historyNotFound: Boolean?,

    getNativeAd: () -> Unit,
    scope: CoroutineScope,
) {

    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                scope.launch {
                    while (this.isActive) {
                        getNativeAd()
                        delay(20000L)
                    }
                }
            }

            else -> {
            }
        }
    }
    GlassComponent()
    Scaffold(
        modifier = modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        containerColor = colorResource(id = com.thezayin.values.R.color.background),
        topBar = {
            Column {
                RecentTopBar(
                    modifier = Modifier,
                    onBackClick = onBackClick,
                )
                if (showNativeAd) {
                    GoogleNativeAd(
                        modifier = Modifier,
                        style = GoogleNativeAdStyle.Small,
                        nativeAd = nativeAd
                    )
                }
            }
        },
        bottomBar = {
            if (showNativeAd) {
                GoogleNativeAd(
                    modifier = Modifier,
                    style = GoogleNativeAdStyle.Small,
                    nativeAd = nativeAd
                )
            }
        }
    ) { paddingValues ->
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
                    modifier = Modifier,
                    list = list
                )
            }
        }
    }
}