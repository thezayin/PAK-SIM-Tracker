package com.thezayin.paksimdetails.ui.language.presentation

import android.app.Application
import android.content.res.Configuration
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thezayin.paksimdetails.framework.extension.functions.getActivity
import com.thezayin.paksimdetails.ui.language.domain.model.Language
import com.thezayin.paksimdetails.ui.language.domain.usecase.GetSelectedLanguageUseCase
import com.thezayin.paksimdetails.ui.language.domain.usecase.SetLanguageUseCase
import com.thezayin.paksimdetails.ui.language.presentation.event.LanguageEvent
import com.thezayin.paksimdetails.ui.language.presentation.state.LanguageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class LanguageViewModel(
    private val getSelectedLanguageUseCase: GetSelectedLanguageUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
    private val application: Application
) : ViewModel() {

    private val _state = MutableStateFlow(LanguageState())
    val state: StateFlow<LanguageState> get() = _state

    init {
        loadSelectedLanguage()
    }

    private fun loadSelectedLanguage() {
        viewModelScope.launch {
            getSelectedLanguageUseCase.invoke().collect { language ->
                _state.value = _state.value.copy(selectedLanguage = language)
                // Apply the language to the app when it's loaded
                language?.let { applyLanguage(it) }
            }
        }
    }

    fun onEvent(event: LanguageEvent) {
        when (event) {
            is LanguageEvent.OnLanguageSelected -> {
                setLanguageUseCase.invoke(event.language)
                _state.value = _state.value.copy(
                    selectedLanguage = event.language,
                    isNextButtonEnabled = true // Enable Next button when language is selected
                )
            }
            is LanguageEvent.OnNextButtonClicked -> {
                _state.value.selectedLanguage?.let { applyLanguage(it) }
                // Move to the next screen after applying the language
            }
        }
    }

    // Apply the selected language to the app's locale
    private fun applyLanguage(language: Language) {
        val locale = when (language) {
            Language.ENGLISH -> Locale("en")
            Language.URDU -> Locale("ur")
        }
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        // Update the app's resources to use the new locale
        val context = application.getActivity()?.applicationContext
        context?.resources?.updateConfiguration(config, context?.resources?.displayMetrics)

        Log.d("LanguageViewModel", "Language applied: ${language.name}")
    }
}
