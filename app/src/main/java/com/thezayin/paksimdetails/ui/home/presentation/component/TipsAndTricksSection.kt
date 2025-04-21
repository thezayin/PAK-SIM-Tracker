package com.thezayin.paksimdetails.ui.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.thezayin.paksimdetails.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview
@Composable
fun TipsAndTricksSection() {
    Column(
        modifier = Modifier.padding(horizontal = 20.sdp)
    ) {
        Card(
            modifier = Modifier
                .padding(top = 40.sdp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.sdp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.background),
            )
        ) {
            Column(
                modifier = Modifier.padding(16.sdp),
                verticalArrangement = Arrangement.spacedBy(8.sdp)
            ) {
                Text(
                    text = stringResource(id = R.string.tips_and_tricks_title), // Fetch title from string resource
                    modifier = Modifier.padding(top = 15.sdp),
                    fontSize = 14.ssp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.abeezee_regular)),
                    color = colorResource(id = R.color.text_color),
                )
                Text(
                    text = stringResource(id = R.string.tips_and_tricks_list), // Fetch list from string resource
                    modifier = Modifier.padding(top = 10.sdp),
                    fontSize = 11.ssp,
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily(Font(R.font.abeezee_regular)),
                    color = colorResource(id = R.color.text_color),
                )
            }
        }
    }
}
