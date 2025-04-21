package com.thezayin.paksimdetails.ui.history.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.thezayin.paksimdetails.ui.history.domain.model.HistoryModel
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
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 20.sdp, horizontal = 15.sdp)
        ) {
            items(lists.reversed().size) { historyItem ->
                HistoryDetails(
                    name = lists[historyItem].name,
                    cnic = lists[historyItem].cnic,
                    address = lists[historyItem].address,
                    number = lists[historyItem].phoneNumber,
                    resultSuccess = lists[historyItem].searchSuccess
                )
            }
        }
    } ?: Text(
        text = stringResource(id = R.string.no_recent_searches), // Use string resource here
        modifier = Modifier.padding(top = 15.sdp),
        fontSize = 12.ssp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.abeezee_regular)),
        color = colorResource(id = R.color.text_color),
    )
}
