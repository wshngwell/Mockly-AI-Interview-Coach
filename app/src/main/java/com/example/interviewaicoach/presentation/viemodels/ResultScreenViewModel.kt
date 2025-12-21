package com.example.interviewaicoach.presentation.viemodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewaicoach.domain.SingleFlowEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ResultScreenViewModel(
    pointsSum: Int,
    countsOfQuestions: Int,
    numberOfSavedQuestions: Int,
    numberOfVoiceAnsweredQuestions: Int,
) : ViewModel() {

    data class State(
        val pointsSum: Int,
        val countsOfQuestions: Int,
        val numberOfSavedQuestions: Int,
        val numberOfVoiceAnsweredQuestions: Int,
    )

    private val _state: MutableStateFlow<State> = MutableStateFlow(
        (State(
            pointsSum,
            countsOfQuestions,
            numberOfSavedQuestions,
            numberOfVoiceAnsweredQuestions
        ))
    )

    val state: StateFlow<State> = _state.asStateFlow()

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    sealed interface Event {
        data object NavigateOnMainScreen : Event
        data object NavigateOnQuestionsScreenAndRetry: Event
    }

    sealed interface Intent {
        data object OnGoToMainScreenButtonClicked : Intent
        data object OnRetryButtonClicked : Intent
    }


    fun sendIntent(intent: Intent) {
        when (intent) {
            Intent.OnGoToMainScreenButtonClicked -> {
                _event.emit(Event.NavigateOnMainScreen)
            }

            Intent.OnRetryButtonClicked -> {
                _event.emit(
                    Event.NavigateOnQuestionsScreenAndRetry
                )
            }
        }

    }

}