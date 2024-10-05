package com.thezayin.common.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    screenName: String = "Servers",
    showPremium: Boolean,
    onBackClick: () -> Unit = {},
    onPremiumClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = com.thezayin.values.R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onBackClick()
                }
                .size(40.dp)
                .padding(10.dp),
        )
        Text(
            text = screenName,
            color = colorResource(id = com.thezayin.values.R.color.black),
            fontSize = 17.sp,
            fontFamily = FontFamily(Font(com.thezayin.values.R.font.abeezee_italic)),
        )

        if (showPremium) {
            Image(
                painter = painterResource(id = com.thezayin.values.R.drawable.ic_crown),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onPremiumClick()
                    }
                    .size(40.dp)
                    .padding(10.dp),
            )
        }
    }
}