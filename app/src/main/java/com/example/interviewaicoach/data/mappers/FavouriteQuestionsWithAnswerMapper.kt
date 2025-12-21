package com.example.interviewaicoach.data.mappers

import com.example.interviewaicoach.data.local.dbModels.QuestionWithAnswerDbModel
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.CorrectAnswerEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionWithAnswerEntity

fun QuestionWithAnswerDbModel.mapToQuestionWithAnswerEntity() = QuestionWithAnswerEntity(
    id = id.toString(),
    questionEntity = QuestionEntity(
        questionContent = questionContent,
        categoryName = categoryName,
        gradeName = gradeName,
        questionTopic = ""
    ),
    correctAnswerEntity = CorrectAnswerEntity(
        answerContent = answerContent
    )
)

fun QuestionWithAnswerEntity.toQuestionWithAnswerDbModel(isSavedByUser: Boolean) =
    QuestionWithAnswerDbModel(
        id = 0,
        questionContent = questionEntity.questionContent,
        categoryName = questionEntity.categoryName,
        gradeName = questionEntity.gradeName,
        answerContent = correctAnswerEntity.answerContent,
        isSavedByUser = isSavedByUser
    )

