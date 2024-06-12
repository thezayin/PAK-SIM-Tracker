package com.thezayin.paksimdetails.presentation.servers.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thezayin.core.R
import com.thezayin.paksimdetails.presentation.activities.MainViewModel
import com.thezayin.paksimdetails.presentation.servers.domain.model.ServerList

@Composable
fun ServerList(
    mainViewModel: MainViewModel,
    navigator: DestinationsNavigator,
    modifier: Modifier,
    serverList: List<ServerList>,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 40.dp)
    ) {
        Text(
            text = "Use VPN to access servers.",
            fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
            color = colorResource(id = R.color.black),
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize()
                .padding(top = 10.dp),
        ) {
            items(serverList.size) { server ->
                Spacer(modifier = Modifier.padding(5.dp))
                ServerItem(
                    server = serverList[server],
                    modifier = Modifier,
                    navigator = navigator,
                    mainViewModel = mainViewModel
                )
                Spacer(modifier = Modifier.padding(5.dp))
            }
        }
    }
}