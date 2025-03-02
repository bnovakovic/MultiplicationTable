package com.bojan.multiplicationpractise.screens.mainscreen

data class MainScreenUiModel(
    val answer: String,
    val mainScreenState: MainScreenState,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val answerTimeoutProgress: Float,
    val difficulty: Difficulty
)