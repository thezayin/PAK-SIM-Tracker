package com.thezayin.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ServerBar(
    onServerClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "More Servers",
            color = colorResource(id = com.thezayin.values.R.color.black),
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(com.thezayin.values.R.font.abeezee_italic)),
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(horizontal = 15.sdp)
                .padding(top = 10.sdp)
                .clickable {
                    onServerClick()
                }
        )
    }
}