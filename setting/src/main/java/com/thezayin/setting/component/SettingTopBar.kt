package com.thezayin.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

@Composable
fun SettingTopBar(
    modifier: Modifier,
    onBackPress: () -> Unit,
    onPremiumPress: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = com.thezayin.values.R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onBackPress()
                }
                .size(40.dp)
                .padding(10.dp),
        )
        Text(
            text = "Setting",
            color = colorResource(id = com.thezayin.values.R.color.black),
            fontSize = 17.sp,
            fontFamily = FontFamily(Font(com.thezayin.values.R.font.abeezee_italic)),
        )

        Image(
            painter = painterResource(id = com.thezayin.values.R.drawable.ic_crown),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onPremiumPress()
                }
                .size(40.dp)
                .padding(10.dp),
        )
    }
}