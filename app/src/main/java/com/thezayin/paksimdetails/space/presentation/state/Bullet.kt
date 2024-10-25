package com.thezayin.paksimdetails.space.presentation.state

data class Bullet(
    val x: Float, // X position of the bullet
    val y: Float, // Y position of the bullet
    val speed: Float = 10f // Speed at which the bullet moves upward
)