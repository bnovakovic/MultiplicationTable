package com.bojan.multiplicationpractise

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import com.bojan.multiplicationpractise.screens.mainscreen.MainScreen
import com.bojan.multiplicationpractise.screens.mainscreen.MainScreenViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = remember { MainScreenViewModel() }
        val uiState by viewModel.uiModel.collectAsState()
        MainScreen(uiState, onAnswer = viewModel::onAction, onStart = viewModel::onStart, onRestart = viewModel::restart)
    }
}