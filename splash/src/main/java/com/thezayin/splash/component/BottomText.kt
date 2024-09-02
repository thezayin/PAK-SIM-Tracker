package com.thezayin.splash.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun BottomText(modifier: Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.sdp),
        textAlign = TextAlign.Center,
        text = "SIM Insights",
        fontSize = 14.ssp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(com.thezayin.values.R.font.abeezee_regular))
    )
}