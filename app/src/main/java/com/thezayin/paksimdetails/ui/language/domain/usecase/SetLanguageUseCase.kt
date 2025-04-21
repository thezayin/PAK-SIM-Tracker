package com.thezayin.paksimdetails.ui.language.domain.usecase

import com.thezayin.paksimdetails.ui.language.domain.model.Language
import com.thezayin.paksimdetails.ui.language.domain.repository.LanguageRepository

interface SetLanguageUseCase {
    fun invoke(language: Language)
}

class SetLanguageUseCaseImpl(
    private val languageRepository: LanguageRepository
) : SetLanguageUseCase {
    override fun invoke(language: Language) {
        languageRepository.setSelectedLanguage(language)
    }
}