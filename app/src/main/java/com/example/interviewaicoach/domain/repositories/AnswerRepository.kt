package com.example.interviewaicoach.domain.repositories

import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.domain.entities.TResult
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerAiFeedbackEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.CorrectAnswerEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import kotlinx.coroutines.flow.Flow

interface AnswerRepository {

    fun loadCorrectAnswer(questionEntity: QuestionEntity): Flow<TResult<CorrectAnswerEntity, LoadingException>>
     fun postUserAnswer(
        questionEntity: QuestionEntity,
        userAnswer: String,
        correctAnswerEntity: CorrectAnswerEntity
    ): Flow<TResult<AnswerAiFeedbackEntity, LoadingException>>
}