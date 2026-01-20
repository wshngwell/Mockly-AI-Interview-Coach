package com.example.interviewaicoach.data.mappers

import com.example.interviewaicoach.data.local.dbModels.QuestionDbModel
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity

fun QuestionDbModel.mapToQuestionEntity() = QuestionEntity(
    questionContent = questionContent,
    categoryName = categoryName,
    gradeName = gradeName,
    questionTopic = questionTopic
)

fun QuestionEntity.toQuestionDbModel(isSavedByUser: Boolean) =
    QuestionDbModel(
        id = 0,
        questionContent = questionContent,
        categoryName = categoryName,
        gradeName = gradeName,
        isSavedByUser = isSavedByUser,
        questionTopic = questionTopic
    )

