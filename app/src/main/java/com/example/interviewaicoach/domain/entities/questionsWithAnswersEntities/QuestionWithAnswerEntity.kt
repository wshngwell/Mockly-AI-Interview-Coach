package com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities

data class QuestionWithAnswerEntity(
    val id: String? = null,
    val questionEntity: QuestionEntity,
    val correctAnswerEntity: CorrectAnswerEntity
)
