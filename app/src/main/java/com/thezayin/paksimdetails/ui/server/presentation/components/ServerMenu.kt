package com.thezayin.paksimdetails.ui.server.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.thezayin.paksimdetails.R
import com.thezayin.paksimdetails.ui.server.domain.model.ServerModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ServerMenu(
    modifier: Modifier,
    list: List<ServerModel>,
    onServerClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 25.sdp)
    ) {
        Text(
            text = stringResource(id = R.string.use_vpn_message),
            fontFamily = FontFamily(Font(R.font.abeezee_italic)),
            color = colorResource(id = R.color.text_color),
            fontSize = 16.ssp,
            modifier = Modifier.padding(horizontal = 10.sdp)
        )
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 10.sdp)
                .fillMaxSize()
                .padding(top = 10.sdp),
        ) {
            items(list.size) { server ->
                Spacer(modifier = Modifier.padding(2.sdp))
                ServerItem(
                    name = list[server].name,
                    url = list[server].url,
                    modifier = Modifier,
                    onServerClick = onServerClick
                )
                Spacer(modifier = Modifier.padding(2.sdp))
            }
        }
    }
}
