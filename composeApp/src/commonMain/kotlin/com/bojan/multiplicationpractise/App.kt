package com.bojan.multiplicationpractise

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.bojan.multiplicationpractise.screens.mainscreen.MainScreen
import com.bojan.multiplicationpractise.screens.mainscreen.MainScreenViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(colors = if (isSystemInDarkTheme()) darkColors() else lightColors()) {
        val viewModel = remember { MainScreenViewModel() }
        val uiState by viewModel.uiModel.collectAsState()
        MainScreen(
            uiModel = uiState,
            onAnswer = viewModel::onAction,
            onStart = viewModel::onStart,
            onRestart = viewModel::restart,
            onDifficultySelected = viewModel::difficultySelected
        )
    }
}