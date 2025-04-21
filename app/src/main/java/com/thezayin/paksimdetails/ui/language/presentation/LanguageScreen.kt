package com.thezayin.paksimdetails.ui.language.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.thezayin.paksimdetails.ui.language.domain.model.Language
import com.thezayin.paksimdetails.ui.language.presentation.components.LanguageCard
import com.thezayin.paksimdetails.ui.language.presentation.event.LanguageEvent
import ir.kaaveh.sdpcompose.sdp
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(
    navigateToOnboarding: () -> Unit,  // This function is not used in the current implementation.
    viewModel: LanguageViewModel = koinInject()
) {
    val state = viewModel.state.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.sdp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LanguageCard(
            language = Language.ENGLISH,
            isSelected = state.selectedLanguage == Language.ENGLISH,
            onClick = { viewModel.onEvent(LanguageEvent.OnLanguageSelected(Language.ENGLISH)) }
        )

        Spacer(modifier = Modifier.height(16.sdp))

        LanguageCard(
            language = Language.URDU,
            isSelected = state.selectedLanguage == Language.URDU,
            onClick = { viewModel.onEvent(LanguageEvent.OnLanguageSelected(Language.URDU)) }
        )

        Spacer(modifier = Modifier.height(32.sdp))

        Button(
            onClick = { viewModel.onEvent(LanguageEvent.OnNextButtonClicked) },
            enabled = state.isNextButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.sdp)
        ) {
            Text(text = "Next")
        }
    }

    if (state.isNextButtonEnabled) {
        navigateToOnboarding()
    }
}