package com.example.interviewaicoach.domain.repositories

import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.domain.entities.TResult
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import kotlinx.coroutines.flow.Flow

interface LoadQuestionRepository {

     fun loadQuestion(
        categoryName: String,
        gradeName: String,
    ): Flow<TResult<QuestionEntity, LoadingException>>

}