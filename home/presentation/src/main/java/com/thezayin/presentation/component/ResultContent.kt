package com.thezayin.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ResultContent(
    modifier: Modifier,
    name: String = "",
    cnic: String = "",
    address: String = "",
    number: String = ""
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.sdp, horizontal = 15.sdp),
        shape = RoundedCornerShape(10.sdp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.background)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.sdp, vertical = 25.sdp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Name",
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.abeezee_regular)),
                color = colorResource(id = R.color.text_color_light),
            )
            Text(
                text = if (name.length < 2) "Data not found in server" else name,
                fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                color = colorResource(id = R.color.black),
                fontSize = 16.ssp,
            )
            Text(
                modifier = Modifier.padding(top = 10.sdp),
                text = "CNIC:",
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.abeezee_regular)),
                color = colorResource(id = R.color.text_color_light),
            )
            Text(
                text = if (cnic.length < 2) "Data not found in server" else cnic,
                fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                color = colorResource(id = R.color.black),
                fontSize = 16.ssp,
            )
            Text(
                modifier = Modifier.padding(top = 10.sdp),
                text = "Address:",
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.abeezee_regular)),
                color = colorResource(id = R.color.text_color_light),
            )
            Text(
                text = if (address.length < 2) "Data not found in server" else address,
                lineHeight = 20.ssp,
                fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                color = colorResource(id = R.color.black),
                fontSize = 16.ssp,
            )
            Text(
                modifier = Modifier.padding(top = 10.sdp),
                text = "Number:",
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.abeezee_regular)),
                color = colorResource(id = R.color.text_color_light),
            )
            Text(
                text = if (number.length < 2) "Data not found in server" else number,
                fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                color = colorResource(id = R.color.black),
                fontSize = 16.ssp,
            )
        }
    }
}