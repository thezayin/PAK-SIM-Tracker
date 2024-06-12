package com.thezayin.paksimdetails.presentation.home.component

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thezayin.paksimdetails.R
import com.thezayin.paksimdetails.presentation.activities.MainViewModel
import com.thezayin.paksimdetails.presentation.activities.dialogs.interstitialAd
import com.thezayin.paksimdetails.presentation.home.HomeViewModel

@Composable
fun SearchBox(
    modifier: Modifier, viewModel: HomeViewModel, mainViewModel: MainViewModel
) {
    val number = remember { mutableStateOf(TextFieldValue()) }
    val showWarning = remember { mutableStateOf(false) }
    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()

    val horizontalGradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xffF57F17),
            Color(0xffFFEE58),
            Color(0xffFFF9C4)
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
            .padding(top = 60.dp, bottom = 20.dp),
    ) {
        Text(
            text = "Enter Number",
            textAlign = TextAlign.Center,
            color = colorResource(id = com.thezayin.core.R.color.black),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(com.thezayin.core.R.font.noto_sans_bold)),
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            TextField(
                value = number.value,
                onValueChange = {
                    number.value = it
                },
                placeholder = {
                    Text(
                        text = "03121234567",
                        color = colorResource(id = com.thezayin.core.R.color.grey),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(com.thezayin.core.R.font.noto_sans_bold)),
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.semi_transparent),
                    unfocusedContainerColor = colorResource(id = R.color.semi_transparent),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black)
                )
            )
            if (showWarning.value) {
                Text(
                    text = "Please enter a valid number",
                    color = colorResource(id = com.thezayin.core.R.color.red),
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(com.thezayin.core.R.font.abeezee_italic)),
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.black),
                ),
                onClick = {
                    if (number.value.text.length == 11) {
                        activity.interstitialAd(
                            scope = scope,
                            viewModel = mainViewModel,
                            showAd = mainViewModel.remoteConfig.showAdOnSearchClick,
                        ) {
                            viewModel.searchNumber(number.value.text)
                            showWarning.value = false
                        }
                    } else {
                        showWarning.value = true
                    }
                },
            ) {
                Text(
                    text = "Search",
                    color = colorResource(id = com.thezayin.core.R.color.white),
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(com.thezayin.core.R.font.noto_sans_regular)),
                )
            }
        }
    }
}