package com.thezayin.paksimdetails.space.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thezayin.paksimdetails.space.domain.model.GameScore
import com.thezayin.paksimdetails.space.domain.usecase.GetHighScoreUseCase
import com.thezayin.paksimdetails.space.domain.usecase.SaveHighScoreUseCase
import com.thezayin.paksimdetails.space.presentation.event.GameEvent
import com.thezayin.paksimdetails.space.presentation.event.GameIntent
import com.thezayin.paksimdetails.space.presentation.state.Bullet
import com.thezayin.paksimdetails.space.presentation.state.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val getHighScoreUseCase: GetHighScoreUseCase,
    private val saveHighScoreUseCase: SaveHighScoreUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    init {
        // Load the high score when the ViewModel is initialized
        viewModelScope.launch {
            val highScore = getHighScoreUseCase.execute()
            _state.update { it.copy(highScore = highScore) }
        }
    }

    fun handleIntent(intent: GameIntent) {
        when (intent) {
            is GameIntent.MoveLeft -> moveLeft()
            is GameIntent.MoveRight -> moveRight()
            is GameIntent.Shoot -> shoot()
            is GameIntent.RestartGame -> restartGame()
        }
    }

    private fun moveLeft() {
        _state.update {
            val newX = (it.spaceshipX - 10).coerceAtLeast(0f)
            it.copy(spaceshipX = newX)
        }
    }

    private fun moveRight() {
        _state.update {
            val newX = (it.spaceshipX + 10).coerceAtMost(100f) // Example boundary check
            it.copy(spaceshipX = newX)
        }
    }

    private fun shoot() {
        _state.update {
            val bullets = it.bullets + Bullet(it.spaceshipX, 600f)
            it.copy(bullets = bullets)
        }
    }

    private fun restartGame() {
        _state.update {
            it.copy(
                highScore = it.highScore,
                bullets = emptyList(),
                enemies = emptyList(),
                isGameOver = false
            )
        }
    }

    // Sample function to handle events
    private fun onGameEvent(event: GameEvent) {
        when (event) {
            is GameEvent.GameOver -> {
                viewModelScope.launch {
                    saveHighScoreUseCase.execute(GameScore("player", _state.value.highScore))
                }
            }

            is GameEvent.ScoreUpdated -> {
                val newScore = event.newScore
                _state.update { state ->
                    if (newScore > state.highScore) {
                        onGameEvent(GameEvent.NewHighScore(newScore)) // Correctly create NewHighScore event
                    }
                    state.copy(highScore = newScore)
                }
            }

            is GameEvent.NewHighScore -> {
                handleNewHighScore(event.newScore) // Correctly access newScore
            }
        }
    }

    private fun handleNewHighScore(newScore: Int) {
        _state.update { it.copy(highScore = newScore, showNewHighScoreMessage = true) }

        viewModelScope.launch {
            saveHighScoreUseCase.execute(GameScore("player", newScore))
        }
    }

    fun resetHighScoreMessageFlag() {
        _state.update { it.copy(showNewHighScoreMessage = false) }
    }
}