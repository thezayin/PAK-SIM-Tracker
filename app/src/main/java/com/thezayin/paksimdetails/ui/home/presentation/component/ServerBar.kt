package com.thezayin.paksimdetails.ui.home.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.thezayin.paksimdetails.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ServerBar(
    onServerClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(20.sdp)
            .fillMaxWidth()
            .height(80.sdp)
            .clickable {
                onServerClick()
            },
        shape = RoundedCornerShape(10.sdp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.background),
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.more_servers),
                color = colorResource(id = R.color.black),
                fontSize = 14.ssp,
                fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(horizontal = 15.sdp)
                    .padding(top = 10.sdp)
            )
        }
    }
}


@Preview
@Composable
fun ServerBarPreview() {
    ServerBar(
        onServerClick = {}
    )
}