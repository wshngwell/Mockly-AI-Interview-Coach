package com.example.interviewaicoach.domain.repositories

import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.domain.entities.TResult
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.GradeEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity

interface LoadQuestionRepository {

    suspend fun loadQuestion(
        categoryName: String,
        gradeEntity: GradeEntity,
    ): TResult<QuestionEntity, LoadingException>

}