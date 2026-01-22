package com.example.interviewaicoach.presentation.viemodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewaicoach.domain.SingleFlowEvent
import com.example.interviewaicoach.presentation.viemodels.DirectionScreenViewModel.Event.OnNavigateToFavouriteQuestionScreen
import com.example.interviewaicoach.presentation.viemodels.DirectionScreenViewModel.Event.OnNavigateToGradeScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DirectionScreenViewModel : ViewModel() {

    data class State(
        val directionInIt: String = ""
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    sealed interface Event {
        data class OnNavigateToGradeScreen(val directionInIt: String) : Event
        data object OnNavigateToFavouriteQuestionScreen : Event

        data object OnSettingsIconClicked : Event
    }

    sealed interface Intent {
        data class OnChangeCurrentDirectionInIt(val directionInIt: String) : Intent
        data object OnConfirmDirectionInIt : Intent
        data object OnFavouriteIconClicked : Intent
        data object OnSettingsIconClicked : Intent
    }

    fun sendIntent(intent: Intent) {
        when (intent) {
            is Intent.OnChangeCurrentDirectionInIt -> {
                _state.update {
                    it.copy(directionInIt = intent.directionInIt)
                }
            }

            Intent.OnConfirmDirectionInIt -> {
                _event.emit(OnNavigateToGradeScreen(state.value.directionInIt))
            }

            Intent.OnFavouriteIconClicked -> _event.emit(OnNavigateToFavouriteQuestionScreen)

            Intent.OnSettingsIconClicked -> _event.emit(Event.OnSettingsIconClicked)
        }
    }

}