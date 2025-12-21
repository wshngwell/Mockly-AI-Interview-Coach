package com.example.interviewaicoach.presentation.viemodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewaicoach.domain.SingleFlowEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GradeScreenViewModel : ViewModel() {

    data class State(
        val gradeName: String = ""
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    sealed interface Event {
        data class OnNavigateToQuestionScreen(val gradeName: String) : Event
    }

    sealed interface Intent {
        data class OnChangeCurrentGrade(val gradeName: String) : Intent
        data object OnConfirmGrade : Intent
    }

    fun sendIntent(intent: Intent) {
        when (intent) {
            is Intent.OnChangeCurrentGrade -> {
                _state.update {
                    it.copy(gradeName = intent.gradeName)
                }
            }

            Intent.OnConfirmGrade -> {
                _event.emit(Event.OnNavigateToQuestionScreen(state.value.gradeName))
            }
        }
    }
}