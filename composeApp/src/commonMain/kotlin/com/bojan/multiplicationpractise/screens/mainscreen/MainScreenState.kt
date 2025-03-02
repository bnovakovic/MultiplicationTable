package com.bojan.multiplicationpractise.screens.mainscreen

sealed class MainScreenState {
    data object Idle : MainScreenState()
    data class Question(val question: String) : MainScreenState()
    data object Correct : MainScreenState()
    data class Wrong(val solution: String) : MainScreenState()
    data class TimeOut(val solution: Int) : MainScreenState()
    data object ChooseDifficulty : MainScreenState()
}