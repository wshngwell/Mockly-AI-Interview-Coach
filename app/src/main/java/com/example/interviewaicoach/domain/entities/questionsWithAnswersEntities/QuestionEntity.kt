package com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities

data class QuestionEntity(
    val questionContent: String,
    val categoryName: String,
    val gradeEntity: GradeEntity
)
