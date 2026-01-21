package com.example.interviewaicoach.presentation

data class FavouriteQuestionsGradeUiModel(
    val questionsWithCheckBoxList: List<QuestionEntityWithChackBoxUiModel> = listOf(),
    val isExpanded: Boolean = false,
)
