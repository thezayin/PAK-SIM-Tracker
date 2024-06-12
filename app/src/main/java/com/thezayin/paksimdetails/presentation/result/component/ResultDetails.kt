package com.thezayin.paksimdetails.presentation.result.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thezayin.core.R
import com.thezayin.paksimdetails.domain.model.SimDataModel

@Composable
fun ResultDetails(
    modifier: Modifier,
    simDetails: SimDataModel
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = com.thezayin.paksimdetails.R.color.semi_transparent)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 35.dp)
        ) {
            Text(
                text = "Name",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
                color = colorResource(id = R.color.text_color_light),
            )
            Text(
                text = if (simDetails.name.length < 2) "Data not found in server" else simDetails.name,
                modifier = Modifier.padding(top = 15.dp),
                fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
                color = colorResource(id = R.color.black),
                fontSize = 24.sp,
            )
            Text(
                text = "CNIC:",
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 20.dp),
                fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
                color = colorResource(id = R.color.text_color_light),
            )
            Text(
                text = if (simDetails.cnic.length < 2) "Data not found in server" else simDetails.cnic,
                modifier = Modifier.padding(top = 15.dp),
                fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
                color = colorResource(id = R.color.black),
                fontSize = 24.sp,
            )
            Text(
                text = "Address:",
                modifier = Modifier.padding(top = 20.dp),
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
                color = colorResource(id = R.color.text_color_light),
            )
            Text(
                text = if (simDetails.address.length < 2) "Data not found in server" else simDetails.address,
                modifier = Modifier.padding(top = 15.dp),
                fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
                color = colorResource(id = R.color.black),
                fontSize = 24.sp,
            )
            Text(
                text = "Number:",
                modifier = Modifier.padding(top = 20.dp),
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
                color = colorResource(id = R.color.text_color_light),
            )
            Text(
                text = if (simDetails.number.length < 2) "Data not found in server" else simDetails.number,
                modifier = Modifier.padding(top = 15.dp),
                fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
                color = colorResource(id = R.color.black),
                fontSize = 24.sp,
            )

        }
    }
}