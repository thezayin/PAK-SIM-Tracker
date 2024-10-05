package com.thezayin.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ir.kaaveh.sdpcompose.sdp

@Preview
@Composable
internal fun RecentTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.sdp)
            .padding(horizontal = 10.sdp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = com.thezayin.values.R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .size(30.sdp)
                .padding(6.sdp),
        )
    }
}