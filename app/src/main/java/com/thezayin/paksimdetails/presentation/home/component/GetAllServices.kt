package com.thezayin.paksimdetails.presentation.home.component

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.thezayin.paksimdetails.R
import com.thezayin.paksimdetails.presentation.activities.MainViewModel
import com.thezayin.paksimdetails.presentation.activities.dialogs.interstitialAd
import com.thezayin.paksimdetails.presentation.destinations.ServerScreenDestination

@Composable
fun GetAllServices(
    modifier: Modifier,
    navigator: DestinationsNavigator,
    mainViewModel: MainViewModel
) {
    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "If you can’t find data select servers",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.thezayin.core.R.font.noto_sans_medium)),
            color = colorResource(id = com.thezayin.core.R.color.black),
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Card(
            onClick = {
                activity.interstitialAd(
                    scope = scope,
                    viewModel = mainViewModel,
                    showAd = mainViewModel.remoteConfig.showAdOnHomeScreenServerSelection
                ) {
                    navigator.navigate(ServerScreenDestination)
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.height(100.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.semi_transparent))
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "More Server",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(com.thezayin.core.R.font.noto_sans_bold)),
                    color = colorResource(id = com.thezayin.core.R.color.black)
                )
                Image(
                    painter = painterResource(id = com.thezayin.core.R.drawable.ic_next),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp),
                )
            }
        }
    }
}