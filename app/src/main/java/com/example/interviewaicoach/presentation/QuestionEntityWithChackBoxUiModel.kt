package com.example.interviewaicoach.presentation

import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity

data class QuestionEntityWithChackBoxUiModel(
    val questionEntity: QuestionEntity,
    val isChecked: Boolean = false,
)
