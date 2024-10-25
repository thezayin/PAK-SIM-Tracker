package com.thezayin.paksimdetails.game.domain.usecase

import com.thezayin.paksimdetails.game.domain.model.NumberSequence
import com.thezayin.paksimdetails.game.domain.repository.MemoryGameRepository

interface GenerateSequenceUseCase {
    suspend operator fun invoke(length: Int): NumberSequence
}
class GenerateSequenceUseCaseImpl(
    private val repository: MemoryGameRepository
) : GenerateSequenceUseCase {
    override suspend fun invoke(length: Int): NumberSequence {
        return repository.generateSequence(length)
    }
}