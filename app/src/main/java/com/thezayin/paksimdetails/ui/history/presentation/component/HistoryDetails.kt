package com.thezayin.paksimdetails.ui.history.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.thezayin.paksimdetails.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview
@Composable
fun HistoryDetails(
    number: String = "0312-1234567",
    name: String? = "John Doe",
    cnic: String? = "12345-1234567-1",
    address: String? = "House # 123, Street # 123, Sector 123, Islamabad",
    resultSuccess: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.sdp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.sdp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.background),
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.sdp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.sdp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = number,
                        fontSize = 12.ssp,
                        color = colorResource(id = R.color.text_color),
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                    )
                    Card(
                        shape = RoundedCornerShape(10.sdp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(id = R.color.background),
                        ),
                        modifier = Modifier
                            .width(70.sdp)
                            .height(20.sdp)
                    )
                    {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 5.sdp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (resultSuccess) stringResource(id = R.string.success) else stringResource(id = R.string.failed),
                                fontSize = 12.ssp,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic,
                                color = colorResource(id = R.color.text_color),
                                fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                            )
                        }
                    }
                }
                if (resultSuccess) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.sdp),
                        verticalArrangement = Arrangement.spacedBy(5.sdp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = name ?: stringResource(id = R.string.name_not_found),
                            fontSize = 12.ssp,
                            color = colorResource(id = R.color.text_color),
                            fontStyle = FontStyle.Italic,
                            fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                        )
                        Text(
                            text = cnic ?: stringResource(id = R.string.cnic_not_found),
                            fontSize = 12.ssp,
                            color = colorResource(id = R.color.text_color),
                            fontStyle = FontStyle.Italic,
                            fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                        )
                        Text(
                            text = address ?: stringResource(id = R.string.address_not_found),
                            fontSize = 12.ssp,
                            color = colorResource(id = R.color.text_color),
                            fontStyle = FontStyle.Italic,
                            fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                        )
                    }
                } else {
                    Text(
                        modifier = Modifier.padding(10.sdp),
                        text = stringResource(id = R.string.result_not_found),
                        fontSize = 12.ssp,
                        color = colorResource(id = R.color.text_color),
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                    )
                }
            }
        }
    }
}