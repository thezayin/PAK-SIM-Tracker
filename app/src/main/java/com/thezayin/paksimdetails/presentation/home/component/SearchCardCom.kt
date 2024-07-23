package com.thezayin.paksimdetails.presentation.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thezayin.paksimdetails.R
import com.thezayin.paksimdetails.presentation.destinations.SearchScreenDestination

@Composable
fun SearchCardCom(
    modifier: Modifier,
    navigator: DestinationsNavigator
) {
    Card(
        
        modifier = modifier
            .padding(25.dp)
            .height(150.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.semi_transparent))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    navigator.navigate(SearchScreenDestination)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Search Number",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(com.thezayin.core.R.font.noto_sans_bold)),
                color = colorResource(id = com.thezayin.core.R.color.black),
                fontSize = 22.sp,
            )
        }
    }
}