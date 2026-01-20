package com.example.interviewaicoach.presentation

import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity

data class FavouriteQuestionsGradeUiModel(
    val questionsList: List<QuestionEntity> = listOf(),
    val isExpanded: Boolean = false,
)
