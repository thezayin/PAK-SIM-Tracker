package com.thezayin.paksimdetails.ui.language.data.repository

import com.thezayin.paksimdetails.framework.pref.PreferencesManager
import com.thezayin.paksimdetails.ui.language.domain.model.Language
import com.thezayin.paksimdetails.ui.language.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class LanguageRepositoryImpl(
    private val preferencesManager: PreferencesManager
) : LanguageRepository {

    private val _selectedLanguage =
        MutableStateFlow<Language?>(preferencesManager.getSelectedLanguage())

    override fun getSelectedLanguage(): Flow<Language?> = _selectedLanguage

    override fun setSelectedLanguage(language: Language) {
        preferencesManager.setSelectedLanguage(language)
        _selectedLanguage.value = language
    }
}
