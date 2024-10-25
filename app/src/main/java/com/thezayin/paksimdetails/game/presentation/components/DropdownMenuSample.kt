package com.thezayin.paksimdetails.game.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DropdownMenuSample(
    selectedDifficulty: String,
    onDifficultySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val difficulties = listOf("Easy", "Medium", "Hard")

    Box {
        Button(onClick = { expanded = true }) {
            Text(
                text = selectedDifficulty,
                modifier = Modifier.padding(8.dp),
                fontSize = 14.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                fontFamily = FontFamily(Font(com.thezayin.values.R.font.noto_sans_medium))
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            difficulties.forEach { difficulty ->
                DropdownMenuItem(
                    text = { Text(difficulty) },
                    onClick = {
                        onDifficultySelected(difficulty)
                        expanded = false
                    })
            }
        }
    }
}

@Preview
@Composable
fun PreviewDropdownMenuSample() {
    DropdownMenuSample(selectedDifficulty = "Easy", onDifficultySelected = {})
}