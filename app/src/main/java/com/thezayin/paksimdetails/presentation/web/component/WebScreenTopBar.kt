package com.thezayin.paksimdetails.presentation.web.component

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thezayin.core.R
import com.thezayin.paksimdetails.presentation.activities.MainViewModel
import com.thezayin.paksimdetails.presentation.activities.dialogs.interstitialAd

@Composable
fun WebScreenTopBar(
    navigator: DestinationsNavigator,
    modifier: Modifier,
    mainViewModel: MainViewModel
) {
    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    activity.interstitialAd(
                        scope = scope,
                        viewModel = mainViewModel,
                        showAd = mainViewModel.remoteConfig.showAdOnWebScreenBackSelection,
                    ) {
                        navigator.navigateUp()
                    }
                }
                .size(40.dp)
                .padding(10.dp),
        )
        Text(
            text = "Servers",
            color = colorResource(id = R.color.black),
            fontSize = 17.sp,
            fontFamily = FontFamily(Font(R.font.abeezee_italic)),
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_vpn),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        activity.interstitialAd(
                            scope = scope,
                            viewModel = mainViewModel,
                            showAd = mainViewModel.remoteConfig.showAdOnWebScreenVPNSelection,
                        )
                    }
                    .size(40.dp)
                    .padding(10.dp),
            )
            if (mainViewModel.remoteConfig.showPremiumButton) {
                Image(
                    painter = painterResource(id = R.drawable.ic_crown),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            activity.interstitialAd(
                                scope = scope,
                                viewModel = mainViewModel,
                                showAd = mainViewModel.remoteConfig.showAdOnWebScreenIAPSelection,
                            )
                        }
                        .size(40.dp)
                        .padding(10.dp),
                )
            }
        }
    }
}