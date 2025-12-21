package com.example.interviewaicoach.domain.repositories

import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.domain.entities.TResult
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerRating
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity

interface AnswerRepository {

    suspend fun loadCorrectAnswer(questionEntity: QuestionEntity): TResult<AnswerEntity, LoadingException>
    suspend fun postUserAnswer(
        questionEntity: QuestionEntity,
        answerEntity: AnswerEntity
    ): TResult<AnswerRating, LoadingException>
}