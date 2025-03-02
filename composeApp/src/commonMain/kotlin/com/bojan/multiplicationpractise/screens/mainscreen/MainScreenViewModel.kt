package com.bojan.multiplicationpractise.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainScreenViewModel : ViewModel() {
    private val _uiModel = MutableStateFlow(
        MainScreenUiModel(
            answer = "",
            mainScreenState = MainScreenState.Idle,
            correctAnswers = 0,
            wrongAnswers = 0,
            answerTimeoutProgress = 0.0f,
            difficulty = Difficulty.MEDIUM
        )
    )
    val uiModel = _uiModel.asStateFlow()

    private val currentAnswerNumber = mutableListOf<Int>()
    private val questionNumbers = mutableListOf<Int>()
    private var currentJob: Job? = null
    private var answerTime = ANSWER_TIME_MEDIUM

    fun onAction(action: NumberGridActions) {
        val state = _uiModel.value.mainScreenState

        if (state is MainScreenState.Question) {
            when (action) {
                NumberGridActions.One -> {
                    addAnswerNumber(1)
                }

                NumberGridActions.Two -> {
                    addAnswerNumber(2)
                }

                NumberGridActions.Three -> {
                    addAnswerNumber(3)
                }

                NumberGridActions.Four -> {
                    addAnswerNumber(4)
                }

                NumberGridActions.Five -> {
                    addAnswerNumber(5)
                }

                NumberGridActions.Six -> {
                    addAnswerNumber(6)
                }

                NumberGridActions.Seven -> {
                    addAnswerNumber(7)
                }

                NumberGridActions.Eight -> {
                    addAnswerNumber(8)
                }

                NumberGridActions.Nine -> {
                    addAnswerNumber(9)
                }

                NumberGridActions.Zero -> {
                    addAnswerNumber(0)
                }

                NumberGridActions.Delete -> {
                    if (currentAnswerNumber.isNotEmpty()) {
                        currentAnswerNumber.removeLast()
                        updateAnswerText()
                    }
                }

                NumberGridActions.OK -> {
                    checkAnswer()
                }
            }
        }
    }

    fun onStart() {
        val state = _uiModel.value.mainScreenState

        if (state == MainScreenState.Idle) {
            _uiModel.value = _uiModel.value.copy(mainScreenState = MainScreenState.ChooseDifficulty)
        }
    }

    fun restart() {
        currentJob?.cancel()
        currentAnswerNumber.clear()
        questionNumbers.clear()
        _uiModel.value = MainScreenUiModel(
            answer = "",
            mainScreenState = MainScreenState.Idle,
            correctAnswers = 0,
            wrongAnswers = 0,
            answerTimeoutProgress = 0.0f,
            difficulty = Difficulty.MEDIUM
        )
    }

    fun difficultySelected(difficulty: Difficulty) {
        answerTime = when(difficulty) {
            Difficulty.EASY -> ANSWER_TIME_EASY
            Difficulty.MEDIUM -> ANSWER_TIME_MEDIUM
            Difficulty.HARD -> ANSWER_TIME_HARD
        }
        _uiModel.value = _uiModel.value.copy(difficulty = difficulty)
        generateRandomQuestion()
    }

    private fun checkAnswer() {
        if (currentAnswerNumber.isEmpty()) return
        val answer = getAnswerNumber()
        val solution = questionNumbers.fold(1) { accumulator, number -> accumulator * number }
        currentJob?.cancel()
        if (answer == solution) {
            val correctAnswers = _uiModel.value.correctAnswers
            _uiModel.value = _uiModel.value.copy(
                mainScreenState = MainScreenState.Correct,
                correctAnswers = correctAnswers + 1,
                answerTimeoutProgress = 0.0f
            )
            scheduleNewAnswer(CORRECT_ANSWER_DELAY)
        } else {
            val wrongAnswers = _uiModel.value.wrongAnswers
            _uiModel.value = _uiModel.value.copy(
                mainScreenState = MainScreenState.Wrong(solution.toString()),
                wrongAnswers = wrongAnswers + 1,
                answerTimeoutProgress = 0.0f
            )
            scheduleNewAnswer(WRONG_ANSWER_DELAY)
        }
    }

    private fun addAnswerNumber(number: Int) {
        if (currentAnswerNumber.size <= MAX_ANSWER_NUMBERS) {
            if (number == 0 && currentAnswerNumber.isEmpty()) {
                return
            }
            currentAnswerNumber.add(number)
            updateAnswerText()
        }
    }

    private fun updateAnswerText() {
        _uiModel.value = _uiModel.value.copy(answer = currentAnswerNumber.joinToString(separator = ""))
    }

    private fun getAnswerNumber() = currentAnswerNumber.joinToString(separator = "") { it.toString() }.toInt()

    private fun generateRandomQuestion() {

        val firstNumber = NUMBERS_TO_MULTIPLY[Random.nextInt(0, NUMBERS_TO_MULTIPLY.lastIndex)]
        val secondNumber = NUMBERS_TO_MULTIPLY[Random.nextInt(0, NUMBERS_TO_MULTIPLY.lastIndex)]
        currentAnswerNumber.clear()
        questionNumbers.clear()

        questionNumbers.add(firstNumber)
        questionNumbers.add(secondNumber)
        setAnswerTimer()
        _uiModel.value = uiModel.value.copy(
            mainScreenState = MainScreenState.Question("$firstNumber x $secondNumber = ?"),
            answer = ""
        )
    }

    private fun scheduleNewAnswer(delay: Long) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            delay(delay)
            generateRandomQuestion()
        }
    }

    private fun setAnswerTimer() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            val steps = 100
            val interval = (answerTime - 1000) / steps // -1000 is because this code always adds one second to delay. Too lazy to fix it now. xD
            var progress: Float

            for (i in 0 until steps + 1) {
                progress = i / steps.toFloat()
                if (i >= steps)  {
                    _uiModel.value = uiModel.value.copy(answerTimeoutProgress = 1.0f)
                } else {
                    _uiModel.value = uiModel.value.copy(answerTimeoutProgress = progress)
                    delay(interval)
                }
            }

            val solution = questionNumbers.fold(1) { accumulator, number -> accumulator * number }
            val wrongAnswers = _uiModel.value.wrongAnswers
            _uiModel.value = _uiModel.value.copy(mainScreenState = MainScreenState.TimeOut(solution), wrongAnswers = wrongAnswers + 1)
            scheduleNewAnswer(WRONG_ANSWER_DELAY)
        }
    }

    companion object {
        const val MAX_ANSWER_NUMBERS = 4
        const val WRONG_ANSWER_DELAY = 2000L
        const val CORRECT_ANSWER_DELAY = 1000L
        const val ANSWER_TIME_EASY = 20000L
        const val ANSWER_TIME_MEDIUM = 10000L
        const val ANSWER_TIME_HARD = 5000L
        val NUMBERS_TO_MULTIPLY = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    }

}