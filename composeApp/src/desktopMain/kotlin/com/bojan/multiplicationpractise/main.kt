package com.bojan.multiplicationpractise

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import multiplicationpractise.composeapp.generated.resources.Res
import multiplicationpractise.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

fun main() = application {
    Window(
        state = rememberWindowState(size = DpSize(600.dp, 900.dp), position = WindowPosition.Aligned(Alignment.Center)),
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
    ) {
        App()
    }
}