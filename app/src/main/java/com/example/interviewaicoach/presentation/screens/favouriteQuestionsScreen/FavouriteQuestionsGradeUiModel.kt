package com.example.interviewaicoach.presentation.screens.favouriteQuestionsScreen

import com.example.interviewaicoach.presentation.screens.favouriteQuestionsScreen.QuestionEntityWithCheckBoxUiModel

data class FavouriteQuestionsGradeUiModel(
    val questionsWithCheckBoxList: List<QuestionEntityWithCheckBoxUiModel> = listOf(),
    val isExpanded: Boolean = false,
)