package com.example.interviewaicoach.presentation.screens.favouriteQuestionsScreen

import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity

data class QuestionEntityWithCheckBoxUiModel(
    val questionEntity: QuestionEntity,
    val isChecked: Boolean = false,
)