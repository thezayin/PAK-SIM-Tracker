package com.thezayin.paksimdetails.ui.home.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.thezayin.paksimdetails.R
import ir.kaaveh.sdpcompose.sdp

@Composable
internal fun HomeTopBar(
    onMenuClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.sdp)
            .padding(horizontal = 10.sdp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onMenuClick()
                }
                .size(25.sdp)
                .padding(6.sdp),
        )
        Image(
            painter = painterResource(id = R.drawable.ic_history),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onHistoryClick()
                }
                .size(20.sdp),
        )
    }
}