package com.example.interviewaicoach.domain.repositories

import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionWithAnswerEntity
import kotlinx.coroutines.flow.SharedFlow

interface FavouriteQuestionsWIthAnswerRepository {

    fun loadFavouritesQuestionsWithAnswer(): SharedFlow<List<QuestionWithAnswerEntity>>

    suspend fun addToFavouriteQuestionWithAnswer(questionWithAnswerEntity: QuestionWithAnswerEntity)

    suspend fun deleteFromFavouriteQuestionWithAnswer(questionId: String)

}