package com.thezayin.paksimdetails.ui.language.presentation.state

import com.thezayin.paksimdetails.ui.language.domain.model.Language

data class LanguageState(
    val selectedLanguage: Language? = null,
    val isNextButtonEnabled: Boolean = false
)