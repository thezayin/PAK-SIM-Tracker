package com.thezayin.paksimdetails.ui.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.thezayin.paksimdetails.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview
@Composable
fun SettingHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(80.sdp),
            shape = RoundedCornerShape(80.sdp),
            onClick = {}
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_main),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 16.ssp,
            fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
            color = colorResource(id = R.color.black),
            modifier = Modifier.padding(top = 10.sdp)
        )
    }
}
