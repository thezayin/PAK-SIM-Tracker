package com.thezayin.paksimdetails.ui.home.presentation.component

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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thezayin.paksimdetails.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun SearchCard(
    modifier: Modifier,
    number: MutableState<TextFieldValue>,
    showWarning: MutableState<Boolean>,
    onSearchClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.sdp)
            .padding(top = 30.sdp),
    ) {
        Text(
            text = stringResource(id = R.string.enter_number),
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.text_color),
            fontSize = 14.ssp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily(Font(R.font.abeezee_italic)),
            modifier = Modifier.padding(bottom = 12.sdp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(40.sdp)) {
            TextField(
                value = number.value,
                onValueChange = {
                    number.value = it
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.number_placeholder),
                        color = colorResource(id = R.color.grey),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.background),
                    unfocusedContainerColor = colorResource(id = R.color.background),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black)
                )
            )
            if (showWarning.value) {
                Text(
                    text = stringResource(id = R.string.invalid_number_warning),
                    color = colorResource(id = R.color.red),
                    fontSize = 10.ssp,
                    fontFamily = FontFamily(Font(R.font.abeezee_italic)),
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
                    if (number.value.text.length != 11) {
                        showWarning.value = true
                        return@Button
                    } else {
                        showWarning.value = false
                        onSearchClick(number.value.text)
                    }
                }
            ) {
                Text(
                    text = stringResource(id = R.string.search_button_label),
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.white),
                    fontSize = 16.ssp,
                    fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                )
            }
        }
    }
}
