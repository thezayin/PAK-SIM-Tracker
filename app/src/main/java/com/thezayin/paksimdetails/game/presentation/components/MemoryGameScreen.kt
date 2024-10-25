package com.thezayin.paksimdetails.game.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thezayin.common.component.GlassComponent
import com.thezayin.paksimdetails.game.presentation.MemoryGameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryGameScreen(
    navigateUp: () -> Unit,
) {
    val viewModel: MemoryGameViewModel = koinInject()
    val coroutineScope = rememberCoroutineScope()

    // UI State
    val displayText by viewModel.displayText.collectAsState()
    val userInput by viewModel.userInput.collectAsState()
    val isInputEnabled by viewModel.isInputEnabled.collectAsState()
    val highscore by viewModel.highscore.collectAsState()

    var selectedDifficulty by remember { mutableStateOf("Easy") }

    GlassComponent()

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        containerColor = colorResource(id = com.thezayin.values.R.color.background),
        topBar = {
            TopAppBar(
                title = { Text("Number Memory Game") },
                navigationIcon = {
                    IconButton(onClick = { navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Difficulty Selection
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Difficulty: ")
                Spacer(modifier = Modifier.width(8.dp))
                DropdownMenuSample(selectedDifficulty) { selectedDifficulty = it }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Display Text
            Text(
                text = displayText,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // User Input
            OutlinedTextField(
                value = userInput,
                onValueChange = { viewModel.onUserInputChanged(it) },
                label = { Text("Enter the sequence") },
                enabled = isInputEnabled,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    viewModel.setDifficulty(selectedDifficulty)
                    viewModel.startGame()
                    coroutineScope.launch {
                        // Display the sequence for 5 seconds
                        delay(5000)
                        viewModel.onSequenceDisplayFinished()
                    }
                }) {
                    Text("Start Game")
                }

                Button(
                    onClick = {
                        viewModel.checkSequence()
                    },
                    enabled = isInputEnabled
                ) {
                    Text("Submit")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Highscore Display
            Text(
                text = "Highscore: $highscore",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview
@Composable
fun PreviewMemoryGameScreen() {
    MemoryGameScreen(navigateUp = {})
}