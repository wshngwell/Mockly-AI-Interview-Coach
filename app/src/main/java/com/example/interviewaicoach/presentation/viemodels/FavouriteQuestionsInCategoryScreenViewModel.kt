package com.example.interviewaicoach.presentation.viemodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewaicoach.domain.SingleFlowEvent
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases.DeleteQuestionFromFavouriteUseCase
import com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases.GetFavouriteQuestionsUseCase
import com.example.interviewaicoach.presentation.FavouriteQuestionsGradeUiModel
import com.example.interviewaicoach.presentation.GradeUIModel
import com.example.interviewaicoach.presentation.QuestionEntityWithChackBoxUiModel
import com.example.interviewaicoach.presentation.convertToString
import com.example.interviewaicoach.presentation.viemodels.FavouriteQuestionsInCategoryScreenViewModel.Event.OnNavigateToQuestionWithAnswerScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouriteQuestionsInCategoryScreenViewModel(
    private val categoryName: String,
    private val getFavouriteQuestionsUseCase: GetFavouriteQuestionsUseCase,
    private val deleteQuestionFromFavouriteUseCase: DeleteQuestionFromFavouriteUseCase
) : ViewModel() {

    data class State(
        val categoryName: String,
        val juniorQuestionsList: FavouriteQuestionsGradeUiModel = FavouriteQuestionsGradeUiModel(),
        val middleQuestionsList: FavouriteQuestionsGradeUiModel = FavouriteQuestionsGradeUiModel(),
        val seniorQuestionsList: FavouriteQuestionsGradeUiModel = FavouriteQuestionsGradeUiModel(),
        val leadQuestionsList: FavouriteQuestionsGradeUiModel = FavouriteQuestionsGradeUiModel(),
        val countOfSelectedQuestions: Int = 0,
        val isEmptyFavouriteList: Boolean = false,
        val isDeletingMode: Boolean = false,
    )

    private val _state = MutableStateFlow(State(categoryName = categoryName))
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    sealed interface Event {
        data class OnNavigateToQuestionWithAnswerScreen(val questionEntity: QuestionEntity) : Event
        data object OnNavigateToChooseFavQuestionsCategoryScreen : Event

        data object BeginInterviewClicked : Event
    }

    sealed interface Intent {

        data class OnQuestionClicked(val questionEntity: QuestionEntity) : Intent
        data object OnGoBackIconClicked : Intent
        data object CancelDeleting : Intent
        data object DeleteSelectedItems : Intent
        data object DeleteAllItems : Intent
        data object OnDeletingModeStateChange : Intent
        data class OnCheckedQuestionStateChange(
            val checked: Boolean,
            val questionEntity: QuestionEntity,
            val gradeUiModel: GradeUIModel
        ) : Intent

        data class OnExpandGradeStateChange(val gradeName: GradeUIModel) : Intent
        data object BeginInterviewClicked : Intent
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
                OnNavigateToQuestionWithAnswerScreen(
                    intent.questionEntity
                )
            )

            is Intent.OnDeletingModeStateChange -> _state.update {
                it.copy(isDeletingMode = it.isDeletingMode.not())
            }

            is Intent.OnCheckedQuestionStateChange -> {
                if (!intent.checked) {
                    _state.update {
                        it.copy(
                            countOfSelectedQuestions = it.countOfSelectedQuestions + 1,
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            countOfSelectedQuestions = it.countOfSelectedQuestions - 1,
                        )
                    }
                }


                when (intent.gradeUiModel) {
                    GradeUIModel.JUNIOR -> {

                        _state.update {
                            it.copy(
                                juniorQuestionsList = it.juniorQuestionsList.copy(
                                    questionsWithCheckBoxList = checkBoxChangingStateInList(
                                        questionsWithCheckBoxList = it.juniorQuestionsList.questionsWithCheckBoxList,
                                        questionEntity = intent.questionEntity
                                    ),
                                )
                            )
                        }
                    }

                    GradeUIModel.MIDDLE -> {
                        _state.update {
                            it.copy(
                                middleQuestionsList = it.middleQuestionsList.copy(
                                    questionsWithCheckBoxList = checkBoxChangingStateInList(
                                        questionsWithCheckBoxList = it.middleQuestionsList.questionsWithCheckBoxList,
                                        questionEntity = intent.questionEntity
                                    )
                                )
                            )
                        }
                    }

                    GradeUIModel.SENIOR -> {
                        _state.update {
                            it.copy(
                                seniorQuestionsList = it.seniorQuestionsList.copy(
                                    questionsWithCheckBoxList = checkBoxChangingStateInList(
                                        questionsWithCheckBoxList = it.seniorQuestionsList.questionsWithCheckBoxList,
                                        questionEntity = intent.questionEntity
                                    )
                                )
                            )
                        }
                    }

                    GradeUIModel.LEAD -> {
                        _state.update {
                            it.copy(
                                leadQuestionsList = it.leadQuestionsList.copy(
                                    questionsWithCheckBoxList = checkBoxChangingStateInList(
                                        questionsWithCheckBoxList = it.leadQuestionsList.questionsWithCheckBoxList,
                                        questionEntity = intent.questionEntity
                                    )
                                )
                            )
                        }
                    }
                }

            }

            Intent.CancelDeleting -> {

                GradeUIModel.entries.forEach {

                    val filteredListOfQuestionsByCategory = when (it) {
                        GradeUIModel.JUNIOR -> convertFavouriteQuestionsGradeUiModelToListOfQuestionEntity(
                            state.value.juniorQuestionsList
                        )

                        GradeUIModel.MIDDLE -> convertFavouriteQuestionsGradeUiModelToListOfQuestionEntity(
                            state.value.middleQuestionsList
                        )

                        GradeUIModel.SENIOR -> convertFavouriteQuestionsGradeUiModelToListOfQuestionEntity(
                            state.value.seniorQuestionsList
                        )

                        GradeUIModel.LEAD -> convertFavouriteQuestionsGradeUiModelToListOfQuestionEntity(
                            state.value.leadQuestionsList
                        )
                    }
                    convertFilterListOfCategoryToFavQuestionsGradeUiModel(
                        filteredListOfQuestionsByCategory,
                        it
                    )
                }
                _state.update {
                    it.copy(
                        countOfSelectedQuestions = 0,
                        isDeletingMode = false
                    )
                }
            }

            Intent.DeleteAllItems -> {
                viewModelScope.launch {
                    deleteQuestionFromFavouriteUseCase(
                        convertFavouriteQuestionsGradeUiModelToListOfStrings(state.value.juniorQuestionsList) +
                                convertFavouriteQuestionsGradeUiModelToListOfStrings(state.value.middleQuestionsList) +
                                convertFavouriteQuestionsGradeUiModelToListOfStrings(state.value.seniorQuestionsList) +
                                convertFavouriteQuestionsGradeUiModelToListOfStrings(state.value.leadQuestionsList)
                    )
                    _state.update {
                        it.copy(
                            countOfSelectedQuestions = 0,
                            isDeletingMode = false
                        )
                    }
                }
            }

            Intent.DeleteSelectedItems -> {
                viewModelScope.launch {
                    deleteQuestionFromFavouriteUseCase(
                        convertFavouriteQuestionsGradeUiModelThatCheckedToListOfStrings(state.value.juniorQuestionsList) +
                                convertFavouriteQuestionsGradeUiModelThatCheckedToListOfStrings(
                                    state.value.middleQuestionsList
                                ) +
                                convertFavouriteQuestionsGradeUiModelThatCheckedToListOfStrings(
                                    state.value.seniorQuestionsList
                                ) +
                                convertFavouriteQuestionsGradeUiModelThatCheckedToListOfStrings(
                                    state.value.leadQuestionsList
                                )
                    )
                    _state.update {
                        it.copy(
                            countOfSelectedQuestions = 0,
                            isDeletingMode = false
                        )
                    }
                }
            }

            Intent.BeginInterviewClicked -> {
                _event.emit(Event.BeginInterviewClicked)
            }
        }
    }

    private fun convertFavouriteQuestionsGradeUiModelToListOfQuestionEntity(juniorQuestionsList: FavouriteQuestionsGradeUiModel) =
        juniorQuestionsList.questionsWithCheckBoxList.map {
            it.questionEntity
        }

    private fun convertFavouriteQuestionsGradeUiModelThatCheckedToListOfStrings(juniorQuestionsList: FavouriteQuestionsGradeUiModel) =
        juniorQuestionsList.questionsWithCheckBoxList
            .filter { it.isChecked }
            .map {
                it.questionEntity.questionContent
            }


    private fun convertFavouriteQuestionsGradeUiModelToListOfStrings(juniorQuestionsList: FavouriteQuestionsGradeUiModel) =
        juniorQuestionsList.questionsWithCheckBoxList.map {
            it.questionEntity.questionContent
        }

    private fun checkBoxChangingStateInList(
        questionsWithCheckBoxList: List<QuestionEntityWithChackBoxUiModel>,
        questionEntity: QuestionEntity,
    ): List<QuestionEntityWithChackBoxUiModel> {
        return questionsWithCheckBoxList.map {
            if (it.questionEntity == questionEntity) {
                it.copy(isChecked = it.isChecked.not())
            } else it
        }
    }

    init {
        viewModelScope.launch {
            filterFavouriteQuestionsWithGrade()
        }
    }

    private fun convertFilterListOfCategoryToFavQuestionsGradeUiModel(
        filteredListOfQuestionsByCategory: List<QuestionEntity>,
        gradeUIModel: GradeUIModel,
    ) {
        val filteredListOfQuestionsByCategoryAndGrade = filteredListOfQuestionsByCategory
            .filter { questionEntity -> questionEntity.gradeName == gradeUIModel.convertToString() }
            .map { questionEntity ->
                QuestionEntityWithChackBoxUiModel(
                    questionEntity = questionEntity,
                    isChecked = false
                )
            }

        when (gradeUIModel) {
            GradeUIModel.JUNIOR -> _state.update {
                it.copy(
                    juniorQuestionsList = it.juniorQuestionsList.copy(
                        questionsWithCheckBoxList =
                            filteredListOfQuestionsByCategoryAndGrade
                    )
                )
            }

            GradeUIModel.SENIOR ->
                _state.update {
                    it.copy(
                        seniorQuestionsList = it.seniorQuestionsList.copy(
                            questionsWithCheckBoxList = filteredListOfQuestionsByCategoryAndGrade
                        )
                    )
                }

            GradeUIModel.LEAD ->
                _state.update {
                    it.copy(
                        leadQuestionsList = it.leadQuestionsList.copy(
                            questionsWithCheckBoxList = filteredListOfQuestionsByCategoryAndGrade
                        )

                    )
                }

            GradeUIModel.MIDDLE ->
                _state.update {
                    it.copy(
                        middleQuestionsList = it.middleQuestionsList.copy(
                            questionsWithCheckBoxList = filteredListOfQuestionsByCategoryAndGrade
                        )
                    )
                }

        }
    }

    private suspend fun filterFavouriteQuestionsWithGrade() {
        getFavouriteQuestionsUseCase().collect { listOfFavQuestions ->
            val filteredListOfQuestionsByCategory = listOfFavQuestions
                .filter { questionEntity -> questionEntity.categoryName == state.value.categoryName }

            if (filteredListOfQuestionsByCategory.isEmpty()) {
                _state.update {
                    it.copy(isEmptyFavouriteList = true)
                }
                return@collect
            }

            GradeUIModel.entries.forEach {
                convertFilterListOfCategoryToFavQuestionsGradeUiModel(
                    filteredListOfQuestionsByCategory,
                    it
                )

            }
        }
    }
}