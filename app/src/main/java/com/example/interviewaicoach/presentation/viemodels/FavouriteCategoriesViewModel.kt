package com.example.interviewaicoach.presentation.viemodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewaicoach.domain.SingleFlowEvent
import com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases.GetFavouriteCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouriteCategoriesViewModel(
    private val getFavouriteCategoriesUseCase: GetFavouriteCategoriesUseCase
) : ViewModel() {

    data class State(
        val listOfCategoriesNames: List<String> = listOf(),
        val currentCategory: String? = null
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    sealed interface Event {
        data class OnNavigateToFavouriteQuestionsInCategoryScreen(val category: String) : Event
        data object OnNavigateToChooseDirectionsScreen : Event
    }

    sealed interface Intent {
        data class OnCategoryClicked(val category: String) : Intent
        data object OnCloseScreen : Intent
    }

    fun sendIntent(intent: Intent) {
        when (intent) {
            is Intent.OnCategoryClicked -> {
                _state.update {
                    it.copy(
                        currentCategory = intent.category
                    )
                }
                _event.emit(
                    Event.OnNavigateToFavouriteQuestionsInCategoryScreen(
                        state.value.currentCategory ?: ""
                    )
                )
            }

            Intent.OnCloseScreen -> _event.emit(Event.OnNavigateToChooseDirectionsScreen)
        }
    }

    init {
        viewModelScope.launch {
            getFavouriteCategoriesUseCase().collect { listOfCategoriesNames ->
                _state.update {
                    it.copy(
                        listOfCategoriesNames = listOfCategoriesNames
                    )
                }
            }
        }
    }
}