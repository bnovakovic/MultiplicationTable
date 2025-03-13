package com.bojan.multiplicationpractise.screens.mainscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.bojan.multiplicationpractise.HorizontalSpacer_s
import com.bojan.multiplicationpractise.VerticalSpacer_l
import com.bojan.multiplicationpractise.VerticalSpacer_s
import com.bojan.multiplicationpractise.VerticalSpacer_xs
import com.bojan.multiplicationpractise.controls.CircleButton
import multiplicationpractise.composeapp.generated.resources.Res
import multiplicationpractise.composeapp.generated.resources.answer_right
import multiplicationpractise.composeapp.generated.resources.answer_wrong
import multiplicationpractise.composeapp.generated.resources.correct
import multiplicationpractise.composeapp.generated.resources.correct_answers
import multiplicationpractise.composeapp.generated.resources.difficulty
import multiplicationpractise.composeapp.generated.resources.easy
import multiplicationpractise.composeapp.generated.resources.footer_text
import multiplicationpractise.composeapp.generated.resources.hard
import multiplicationpractise.composeapp.generated.resources.medium
import multiplicationpractise.composeapp.generated.resources.ok
import multiplicationpractise.composeapp.generated.resources.restart
import multiplicationpractise.composeapp.generated.resources.start
import multiplicationpractise.composeapp.generated.resources.time_out
import multiplicationpractise.composeapp.generated.resources.wrong_answer
import multiplicationpractise.composeapp.generated.resources.wrong_answers
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainScreen(
    uiModel: MainScreenUiModel,
    onAnswer: (NumberGridActions) -> Unit,
    onStart: () -> Unit,
    onRestart: () -> Unit,
    onDifficultySelected: (Difficulty) -> Unit
) {
    Box(modifier = Modifier.background(MaterialTheme.colors.surface).padding(16.dp).fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.widthIn(max = 500.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Header(uiModel, onRestart)
            VerticalSpacer_l()
            Info(uiModel, onStart, onDifficultySelected)
            VerticalSpacer_l()
            NumbersGrid(
                onAction = onAnswer
            )
            VerticalSpacer_s()
            Spacer(modifier = Modifier.weight(1.0f))
            Footer()
        }
    }
}


@Composable
fun Header(uiModel: MainScreenUiModel, onRestart: () -> Unit) {
    Row(modifier = Modifier.height(90.dp), verticalAlignment = Alignment.CenterVertically) {
        when (uiModel.mainScreenState) {
            is MainScreenState.Correct,
            is MainScreenState.Wrong,
            is MainScreenState.Question,
            is MainScreenState.TimeOut -> {
                if (uiModel.mainScreenState != MainScreenState.Idle) {
                    Column(modifier = Modifier.weight(1.0f), horizontalAlignment = Alignment.Start) {
                        val difficultyText = when (uiModel.difficulty) {
                            Difficulty.EASY -> stringResource(Res.string.easy)
                            Difficulty.MEDIUM -> stringResource(Res.string.medium)
                            Difficulty.HARD -> stringResource(Res.string.hard)
                        }
                        Text(
                            text = stringResource(Res.string.difficulty, difficultyText),
                            color = MaterialTheme.colors.onSurface,
                            fontSize = TextUnit(18.0f, TextUnitType.Sp)
                        )
                        VerticalSpacer_xs()
                        Text(
                            text = stringResource(Res.string.correct_answers, uiModel.correctAnswers),
                            color = MaterialTheme.colors.onSurface,
                            fontSize = TextUnit(18.0f, TextUnitType.Sp)
                        )
                        VerticalSpacer_xs()
                        Text(
                            text = stringResource(Res.string.wrong_answers, uiModel.wrongAnswers),
                            color = MaterialTheme.colors.onSurface,
                            fontSize = TextUnit(18.0f, TextUnitType.Sp)
                        )
                    }
                    Row(modifier = Modifier.weight(1.0f), horizontalArrangement = Arrangement.End) {
                        IconButton(onClick = onRestart) {
                            Icon(
                                bitmap = imageResource(Res.drawable.restart),
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                }
            }

            else -> {}
        }
    }
}

@Composable
fun Info(uiModel: MainScreenUiModel, onStartPlaying: () -> Unit, onDifficultySelected: (Difficulty) -> Unit ) {
    val state = uiModel.mainScreenState

    Column(
        modifier = Modifier.height(170.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (state) {
            is MainScreenState.Idle -> {
                Box(
                    modifier = Modifier
                        .size(200.dp, 100.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(MaterialTheme.colors.primary)
                        .clickable { onStartPlaying() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.start),
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = TextUnit(24.0f, TextUnitType.Sp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            is MainScreenState.Correct -> {
                Image(
                    painter = painterResource(Res.drawable.answer_right),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
                )
                Text(
                    text = stringResource(Res.string.correct),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = TextUnit(36.0f, TextUnitType.Sp),
                    lineHeight = TextUnit(40.0f, TextUnitType.Sp),
                )
            }

            is MainScreenState.ChooseDifficulty -> {
                Row {
                    Button(
                        onClick = { onDifficultySelected(Difficulty.EASY) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    ) {
                        Text(text = stringResource(Res.string.easy), color = MaterialTheme.colors.onSecondary)
                    }
                    HorizontalSpacer_s()
                    Button(
                        onClick = { onDifficultySelected(Difficulty.MEDIUM) },
                    ) {
                        Text(text = stringResource(Res.string.medium), color = MaterialTheme.colors.onPrimary)
                    }
                    HorizontalSpacer_s()
                    Button(
                        onClick = { onDifficultySelected(Difficulty.HARD) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
                    ) {
                        Text(text = stringResource(Res.string.hard), color = MaterialTheme.colors.onError)
                    }

                }
            }

            is MainScreenState.Question -> {
                val animatedProgress by animateFloatAsState(targetValue = uiModel.answerTimeoutProgress)
                val progressColor = if (animatedProgress < 0.8f) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.error
                }
                LinearProgressIndicator(progress = animatedProgress, modifier = Modifier.fillMaxWidth(), color = progressColor)
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    text = state.question,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = TextUnit(64.0f, TextUnitType.Sp),
                    lineHeight = TextUnit(74.0f, TextUnitType.Sp),
                )
                VerticalSpacer_l()
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    text = uiModel.answer,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = TextUnit(48.0f, TextUnitType.Sp),
                    lineHeight = TextUnit(58.0f, TextUnitType.Sp)
                )
            }

            is MainScreenState.Wrong -> {
                Image(
                    painter = painterResource(Res.drawable.answer_wrong),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.error)
                )
                Text(
                    text = stringResource(Res.string.wrong_answer, state.solution),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = TextUnit(36.0f, TextUnitType.Sp),
                    maxLines = 2,
                    lineHeight = TextUnit(40.0f, TextUnitType.Sp),
                    textAlign = TextAlign.Center
                )
            }

            is MainScreenState.TimeOut -> {
                Image(
                    painter = painterResource(Res.drawable.answer_wrong),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.error)
                )
                Text(
                    text = stringResource(Res.string.time_out, state.solution),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = TextUnit(36.0f, TextUnitType.Sp),
                    maxLines = 2,
                    lineHeight = TextUnit(40.0f, TextUnitType.Sp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun NumbersGrid(onAction: (NumberGridActions) -> Unit) {

    Column(modifier = Modifier) {
        Row {
            CircleButton(text = "1", onClick = { onAction(NumberGridActions.One) })
            HorizontalSpacer_s()
            CircleButton(text = "2", onClick = { onAction(NumberGridActions.Two) })
            HorizontalSpacer_s()
            CircleButton(text = "3", onClick = { onAction(NumberGridActions.Three) })
        }
        VerticalSpacer_s()
        Row {
            CircleButton(text = "4", onClick = { onAction(NumberGridActions.Four) })
            HorizontalSpacer_s()
            CircleButton(text = "5", onClick = { onAction(NumberGridActions.Five) })
            HorizontalSpacer_s()
            CircleButton(text = "6", onClick = { onAction(NumberGridActions.Six) })
        }
        VerticalSpacer_s()
        Row {
            CircleButton(text = "7", onClick = { onAction(NumberGridActions.Seven) })
            HorizontalSpacer_s()
            CircleButton(text = "8", onClick = { onAction(NumberGridActions.Eight) })
            HorizontalSpacer_s()
            CircleButton(text = "9", onClick = { onAction(NumberGridActions.Nine) })
        }
        VerticalSpacer_s()
        Row(horizontalArrangement = Arrangement.Center) {
            CircleButton(text = "C", onClick = { onAction(NumberGridActions.Delete) })
            HorizontalSpacer_s()
            CircleButton(text = "0", onClick = { onAction(NumberGridActions.Zero) })
            HorizontalSpacer_s()
            CircleButton(text = stringResource(Res.string.ok), onClick = { onAction(NumberGridActions.OK) })
        }
    }
}

@Composable
fun Footer() {
    Text(stringResource(Res.string.footer_text), color = MaterialTheme.colors.onSurface)
}