package com.thezayin.paksimdetails.presentation.settings.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thezayin.paksimdetails.R

@Preview
@Composable
fun SettingHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(100.dp),
            shape = RoundedCornerShape(100.dp),
            onClick = {}
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_main),
                contentDescription = "Logo"
            )
        }
        Text(
            text = "Pakistan SIM Database",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(com.thezayin.core.R.font.noto_sans_medium)),
            color = colorResource(id = com.thezayin.core.R.color.black),
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}