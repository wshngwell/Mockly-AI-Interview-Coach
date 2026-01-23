package com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities

data class AnswerAiFeedbackEntity(
    val ratingFromAi: Int,
    val goodPartAnswer: List<String>,
    val badPartAnswer: List<String>,
)
