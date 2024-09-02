package com.thezayin.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.domain.model.ResultModel
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    nativeAd: NativeAd?,
    result: ResultModel?,
    resultNotFound: Boolean?,
    showNativeAd: Boolean?,
    onPremiumClick: () -> Unit,
    onMenuClick: () -> Unit,
    onServerClick: () -> Unit,
    onSearchClick: (String) -> Unit,
) {
    val number = remember { mutableStateOf(TextFieldValue()) }
    val showWarning = remember { mutableStateOf(false) }
    Scaffold(modifier = modifier
        .navigationBarsPadding()
        .statusBarsPadding(),
        containerColor = colorResource(id = com.thezayin.values.R.color.background),
        topBar = {
            Column {
                TopBar(
                    modifier = Modifier, onMenuClick = onMenuClick, onPremiumClick = onPremiumClick
                )
                ServerBar(onServerClick = onServerClick)
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (showNativeAd == true) {
                    GoogleNativeAd(
                        modifier = Modifier, style = GoogleNativeAdStyle.Small, nativeAd = nativeAd
                    )
                }
            }

        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchCard(
                modifier = Modifier,
                number = number,
                showWarning = showWarning,
                onSearchClick = onSearchClick
            )
            result?.let {
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