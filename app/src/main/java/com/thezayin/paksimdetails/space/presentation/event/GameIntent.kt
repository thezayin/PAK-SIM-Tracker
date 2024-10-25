package com.thezayin.paksimdetails.space.presentation.event

sealed class GameIntent {
    object MoveLeft : GameIntent()
    object MoveRight : GameIntent()
    object Shoot : GameIntent()
    object RestartGame : GameIntent()
}