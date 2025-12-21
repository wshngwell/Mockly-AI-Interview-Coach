package com.example.interviewaicoach.data.mappers

import com.example.interviewaicoach.data.local.dbModels.GradeDbModel
import com.example.interviewaicoach.data.local.dbModels.QuestionWithAnswerDbModel
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.GradeEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionWithAnswerEntity

fun QuestionWithAnswerDbModel.mapToQuestionWithAnswerEntity() = QuestionWithAnswerEntity(
    id = id.toString(),
    questionEntity = QuestionEntity(
        questionContent = questionContent,
        categoryName = categoryName,
        gradeEntity = gradeDbModel.toGradeEntity()
    ),
    answerEntity = AnswerEntity(
        answerContent = answerContent
    )
)

fun QuestionWithAnswerEntity.toQuestionWithAnswerDbModel() = QuestionWithAnswerDbModel(
    id = 0,
    questionContent = questionEntity.questionContent,
    categoryName = questionEntity.categoryName,
    gradeDbModel = questionEntity.gradeEntity.toGradeDbModel(),
    answerContent = answerEntity.answerContent,
    isSavedByUser = true
)

fun GradeDbModel.toGradeEntity() = when (this) {
    GradeDbModel.JUNIOR -> GradeEntity.JUNIOR
    GradeDbModel.MIDDLE -> GradeEntity.MIDDLE
    GradeDbModel.SENIOR -> GradeEntity.SENIOR
    GradeDbModel.LEAD -> GradeEntity.LEAD
}

fun GradeEntity.toGradeDbModel() = when (this) {
    GradeEntity.JUNIOR -> GradeDbModel.JUNIOR
    GradeEntity.MIDDLE -> GradeDbModel.MIDDLE
    GradeEntity.SENIOR -> GradeDbModel.SENIOR
    GradeEntity.LEAD -> GradeDbModel.LEAD
}
