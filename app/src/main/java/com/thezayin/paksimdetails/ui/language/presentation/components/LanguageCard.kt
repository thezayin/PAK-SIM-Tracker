package com.thezayin.paksimdetails.ui.language.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.thezayin.paksimdetails.ui.language.domain.model.Language
import ir.kaaveh.sdpcompose.sdp

@Composable
fun LanguageCard(
    language: Language,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(150.sdp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.sdp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Centered language label.
            Text(
                text = if (language == Language.ENGLISH) "English" else "اردو",
                modifier = Modifier.align(Alignment.Center)
            )
            // Top-right circle shows a check icon if the card is selected.
            Surface(
                shape = CircleShape,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                modifier = Modifier
                    .size(24.sdp)
                    .align(Alignment.TopEnd)
                    .padding(8.sdp)
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.padding(2.sdp)
                    )
                }
            }
        }
    }
}
