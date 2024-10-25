package com.thezayin.paksimdetails.game.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thezayin.paksimdetails.game.domain.usecase.CheckSequenceUseCase
import com.thezayin.paksimdetails.game.domain.usecase.GenerateSequenceUseCase
import com.thezayin.paksimdetails.game.domain.usecase.GetHighscoreUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MemoryGameViewModel(
    private val generateSequenceUseCase: GenerateSequenceUseCase,
    private val checkSequenceUseCase: CheckSequenceUseCase,
    private val getHighscoreUseCase: GetHighscoreUseCase
) : ViewModel() {

    private val _displayText = MutableStateFlow("Press Start to Play!")
    val displayText: StateFlow<String> = _displayText

    private val _userInput = MutableStateFlow("")
    val userInput: StateFlow<String> = _userInput

    private val _isInputEnabled = MutableStateFlow(false)
    val isInputEnabled: StateFlow<Boolean> = _isInputEnabled

    private val _highscore = MutableStateFlow(0)
    val highscore: StateFlow<Int> = _highscore

    private var actualSequence: String = ""
    private var sequenceLength: Int = 5

    init {
        loadHighscore()
    }

    fun setDifficulty(level: String) {
        sequenceLength = when (level) {
            "Easy" -> 5
            "Medium" -> 7
            "Hard" -> 10
            else -> 5
        }
    }

    fun startGame() {
        viewModelScope.launch {
            val numberSequence = generateSequenceUseCase(sequenceLength)
            actualSequence = numberSequence.sequence
            _displayText.value = actualSequence
            _userInput.value = ""
            _isInputEnabled.value = false
        }
    }

    fun onSequenceDisplayFinished() {
        viewModelScope.launch {
            _displayText.value = "Enter the sequence from memory:"
            _isInputEnabled.value = true
        }
    }

    fun onUserInputChanged(input: String) {
        _userInput.value = input
    }

    fun checkSequence() {
        viewModelScope.launch {
            val isCorrect = checkSequenceUseCase(userInput.value, actualSequence)
            if (isCorrect) {
                _highscore.value += 1
                _displayText.value = "Correct! Well done!\nHighscore: ${_highscore.value}"
            } else {
                _displayText.value = "Incorrect. The correct sequence was: $actualSequence\nHighscore: ${_highscore.value}"
            }
            _isInputEnabled.value = false
        }
    }

    private fun loadHighscore() {
        viewModelScope.launch {
            _highscore.value = getHighscoreUseCase()
        }
    }
}
