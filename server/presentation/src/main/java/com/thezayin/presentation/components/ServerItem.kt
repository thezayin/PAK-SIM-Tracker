package com.thezayin.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview
@Composable
fun ServerItem(
    modifier: Modifier = Modifier,
    name: String = "Server Name",
    url: String = "Server URL",
    onServerClick: (String) -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = com.thezayin.values.R.color.background)
        ),
        onClick = {
            onServerClick(url)
        },
        shape = RoundedCornerShape(10.sdp),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 15.sdp, vertical = 25.sdp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                fontSize = 18.ssp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(com.thezayin.values.R.font.abeezee_italic)),
                color = colorResource(id = com.thezayin.values.R.color.text_color)
            )
            Image(
                painter = painterResource(id = com.thezayin.values.R.drawable.ic_next),
                contentDescription = null,
                modifier = Modifier
                    .size(16.sdp),
            )
        }
    }
}