package com.thezayin.paksimdetails.ui.common.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thezayin.paksimdetails.R

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    screenName: String = "Servers",
    onBackClick: () -> Unit = {},
    onPremiumClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onBackClick()
                }
                .size(40.dp)
                .padding(10.dp),
        )
        Text(
            text = screenName,
            color = colorResource(id = R.color.black),
            fontSize = 17.sp,
            fontFamily = FontFamily(Font(R.font.abeezee_italic)),
        )

        Image(
            painter = painterResource(id = R.drawable.ic_crown),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onPremiumClick()
                }
                .size(40.dp)
                .padding(10.dp),
        )
    }
}