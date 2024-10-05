package com.thezayin.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.thezayin.domain.model.HistoryModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview
@Composable
fun HistoryList(

    modifier: Modifier = Modifier,
    list: List<HistoryModel>? = emptyList()
) {
    list?.let { lists ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.sdp, horizontal = 15.sdp)
        ) {
            items(lists.reversed()) { historyItem ->
                HistoryDetails(
                    name = historyItem.name,
                    cnic = historyItem.cnic,
                    address = historyItem.address,
                    number = historyItem.phoneNumber,
                    resultSuccess = historyItem.searchSuccess
                )
            }
        }
    } ?: Text(
        text = "No recent searches",
        modifier = Modifier.padding(top = 15.sdp),
        fontSize = 12.ssp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(com.thezayin.values.R.font.abeezee_regular)),
        color = colorResource(id = com.thezayin.values.R.color.text_color),
    )
}