package com.thezayin.paksimdetails.ui.result.presentation.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import com.thezayin.paksimdetails.R

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
                text = stringResource(id = R.string.name_label), // Use string resource here
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.abeezee_regular)),
                color = colorResource(id = R.color.text_color_light),
            )
            Text(
                text = if (name.length < 2) stringResource(id = R.string.data_not_found) else name,
                fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                color = colorResource(id = R.color.black),
                fontSize = 16.ssp,
            )
            Text(
                modifier = Modifier.padding(top = 10.sdp),
                text = stringResource(id = R.string.cnic_label), // Use string resource here
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.abeezee_regular)),
                color = colorResource(id = R.color.text_color_light),
            )
            Text(
                text = if (cnic.length < 2) stringResource(id = R.string.data_not_found) else cnic,
                fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                color = colorResource(id = R.color.black),
                fontSize = 16.ssp,
            )
            Text(
                modifier = Modifier.padding(top = 10.sdp),
                text = stringResource(id = R.string.address_label), // Use string resource here
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.abeezee_regular)),
                color = colorResource(id = R.color.text_color_light),
            )
            Text(
                text = if (address.length < 2) stringResource(id = R.string.data_not_found) else address,
                lineHeight = 20.ssp,
                fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                color = colorResource(id = R.color.black),
                fontSize = 16.ssp,
            )
            Text(
                modifier = Modifier.padding(top = 10.sdp),
                text = stringResource(id = R.string.number_label), // Use string resource here
                fontSize = 10.ssp,
                fontFamily = FontFamily(Font(R.font.abeezee_regular)),
                color = colorResource(id = R.color.text_color_light),
            )
            Text(
                text = if (number.length < 2) stringResource(id = R.string.data_not_found) else number,
                fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                color = colorResource(id = R.color.black),
                fontSize = 16.ssp,
            )
        }
    }
}
