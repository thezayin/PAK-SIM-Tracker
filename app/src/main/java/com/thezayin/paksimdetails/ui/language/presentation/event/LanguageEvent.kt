package com.thezayin.paksimdetails.ui.language.presentation.event

import com.thezayin.paksimdetails.ui.language.domain.model.Language

sealed class LanguageEvent {
    data class OnLanguageSelected(val language: Language) : LanguageEvent()
    object OnNextButtonClicked : LanguageEvent()
}