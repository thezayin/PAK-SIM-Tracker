package com.thezayin.paksimdetails.presentation.servers.presentation.components

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thezayin.core.R
import com.thezayin.paksimdetails.presentation.activities.MainViewModel
import com.thezayin.paksimdetails.presentation.activities.dialogs.interstitialAd
import com.thezayin.paksimdetails.presentation.destinations.WebViewScreenDestination
import com.thezayin.paksimdetails.presentation.servers.domain.model.ServerList

@Composable
fun ServerItem(
    mainViewModel: MainViewModel,
    navigator: DestinationsNavigator,
    modifier: Modifier,
    server: ServerList,
) {
    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = com.thezayin.paksimdetails.R.color.semi_transparent)
        ),
        onClick = {
            activity.interstitialAd(
                scope = scope,
                viewModel = mainViewModel,
                showAd = mainViewModel.remoteConfig.showAdOnServerScreenServerSelection,
            ) {
                navigator.navigate(WebViewScreenDestination(server.serverUrl))
            }
        },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.height(100.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = server.serverName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                color = colorResource(id = R.color.black)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp),
            )
        }
    }
}