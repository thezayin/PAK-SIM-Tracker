package com.thezayin.paksimdetails.space.presentation.state

data class Enemy(
    val x: Float, // X position on the screen
    val y: Float, // Y position on the screen
    val speed: Float = 5f // Speed at which the enemy moves
)