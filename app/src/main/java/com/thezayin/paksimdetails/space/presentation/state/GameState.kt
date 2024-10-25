package com.thezayin.paksimdetails.space.presentation.state

data class GameState(
    val spaceshipX: Float = 0f,
    val bullets: List<Bullet> = emptyList(),
    val enemies: List<Enemy> = emptyList(),
    val highScore: Int = 0,
    val isGameOver: Boolean = false,
    val showNewHighScoreMessage: Boolean = false // New flag for UI feedback
)