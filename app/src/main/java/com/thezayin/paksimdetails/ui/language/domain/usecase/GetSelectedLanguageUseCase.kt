package com.thezayin.paksimdetails.ui.language.domain.usecase

import com.thezayin.paksimdetails.ui.language.domain.model.Language
import com.thezayin.paksimdetails.ui.language.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow

interface GetSelectedLanguageUseCase {
    fun invoke(): Flow<Language?>
}

class GetSelectedLanguageUseCaseImpl(
    private val languageRepository: LanguageRepository
) : GetSelectedLanguageUseCase {
    override fun invoke(): Flow<Language?> {
        return languageRepository.getSelectedLanguage()
    }
}