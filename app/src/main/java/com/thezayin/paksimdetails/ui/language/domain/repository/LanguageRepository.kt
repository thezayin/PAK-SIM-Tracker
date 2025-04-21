package com.thezayin.paksimdetails.ui.language.domain.repository

import com.thezayin.paksimdetails.ui.language.domain.model.Language
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    fun getSelectedLanguage(): Flow<Language?>
    fun setSelectedLanguage(language: Language)
}