package com.example.interviewaicoach.presentation.viemodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewaicoach.domain.SingleFlowEvent
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases.GetFavouriteQuestionsUseCase
import com.example.interviewaicoach.presentation.FavouriteQuestionsGradeUiModel
import com.example.interviewaicoach.presentation.GradeUIModel
import com.example.interviewaicoach.presentation.convertToString
import com.example.interviewaicoach.utils.myLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouriteQuestionsInCategoryScreenViewModel(
    private val categoryName: String,
    private val getFavouriteQuestionsUseCase: GetFavouriteQuestionsUseCase
) : ViewModel() {

    data class State(
        val categoryName: String,
        val juniorQuestionsList: FavouriteQuestionsGradeUiModel = FavouriteQuestionsGradeUiModel(),
        val middleQuestionsList: FavouriteQuestionsGradeUiModel = FavouriteQuestionsGradeUiModel(),
        val seniorQuestionsList: FavouriteQuestionsGradeUiModel = FavouriteQuestionsGradeUiModel(),
        val leadQuestionsList: FavouriteQuestionsGradeUiModel = FavouriteQuestionsGradeUiModel(),
    )

    private val _state = MutableStateFlow(State(categoryName = categoryName))
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    sealed interface Event {
        data class OnNavigateToQuestionWithAnswerScreen(val questionEntity: QuestionEntity) : Event
        data object OnNavigateToChooseFavQuestionsCategoryScreen : Event
    }

    sealed interface Intent {

        data class OnQuestionClicked(val questionEntity: QuestionEntity) : Intent
        data object OnGoBackIconClicked : Intent
        data class OnExpandGradeStateChange(val gradeName: GradeUIModel) : Intent
    }

    fun sendIntent(intent: Intent) {
        when (intent) {

            is Intent.OnGoBackIconClicked -> _event.emit(Event.OnNavigateToChooseFavQuestionsCategoryScreen)
            is Intent.OnExpandGradeStateChange -> {
                when (intent.gradeName) {
                    GradeUIModel.JUNIOR -> _state.update {
                        it.copy(
                            juniorQuestionsList = it.juniorQuestionsList.copy(
                                isExpanded = it.juniorQuestionsList.isExpanded.not()
                            )
                        )
                    }

                    GradeUIModel.MIDDLE -> _state.update {
                        it.copy(
                            middleQuestionsList = it.middleQuestionsList.copy(
                                isExpanded = it.middleQuestionsList.isExpanded.not()
                            )
                        )
                    }

                    GradeUIModel.SENIOR -> _state.update {
                        it.copy(
                            seniorQuestionsList = it.seniorQuestionsList.copy(
                                isExpanded = it.seniorQuestionsList.isExpanded.not()
                            )
                        )
                    }

                    GradeUIModel.LEAD -> _state.update {
                        it.copy(
                            leadQuestionsList = it.leadQuestionsList.copy(
                                isExpanded = it.leadQuestionsList.isExpanded.not()
                            )
                        )
                    }
                }

            }

            is Intent.OnQuestionClicked -> _event.emit(
                Event.OnNavigateToQuestionWithAnswerScreen(
                    intent.questionEntity
                )
            )
        }
    }

    init {
        viewModelScope.launch {
            filterFavouriteQuestionsWithGrade()
        }
    }

    private suspend fun filterFavouriteQuestionsWithGrade() {
        getFavouriteQuestionsUseCase().collect { listOfFavQuestions ->
            val filteredListOfQuestionsByCategory = listOfFavQuestions
                .filter { questionEntity -> questionEntity.categoryName == state.value.categoryName }


            GradeUIModel.entries.forEach {
                val filteredListOfQuestionsByCategoryAndGrade = filteredListOfQuestionsByCategory
                    .filter { questionEntity -> questionEntity.gradeName == it.convertToString() }
                myLog(filteredListOfQuestionsByCategory.toString())

                when (it) {
                    GradeUIModel.JUNIOR -> _state.update {
                        it.copy(
                            juniorQuestionsList = FavouriteQuestionsGradeUiModel(
                                questionsList = filteredListOfQuestionsByCategoryAndGrade
                            )
                        )
                    }

                    GradeUIModel.SENIOR ->
                        _state.update {
                            it.copy(
                                seniorQuestionsList = FavouriteQuestionsGradeUiModel(
                                    questionsList = filteredListOfQuestionsByCategoryAndGrade
                                )
                            )
                        }

                    GradeUIModel.LEAD ->
                        _state.update {
                            it.copy(
                                leadQuestionsList = FavouriteQuestionsGradeUiModel(
                                    questionsList = filteredListOfQuestionsByCategoryAndGrade
                                )
                            )
                        }

                    GradeUIModel.MIDDLE ->
                        _state.update {
                            it.copy(
                                middleQuestionsList = FavouriteQuestionsGradeUiModel(
                                    questionsList = filteredListOfQuestionsByCategoryAndGrade
                                )
                            )
                        }

                }
            }
        }
    }
}