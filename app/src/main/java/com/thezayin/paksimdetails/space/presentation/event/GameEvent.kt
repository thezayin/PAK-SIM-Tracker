package com.thezayin.paksimdetails.space.presentation.event

sealed class GameEvent {
    object GameOver : GameEvent()
    data class ScoreUpdated(val newScore: Int) : GameEvent()
    data class NewHighScore(val newScore: Int) : GameEvent() // Ensure this has a data field
}