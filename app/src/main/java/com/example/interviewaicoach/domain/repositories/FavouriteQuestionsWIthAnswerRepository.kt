package com.example.interviewaicoach.domain.repositories

import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionWithAnswerEntity
import kotlinx.coroutines.flow.SharedFlow

interface FavouriteQuestionsWIthAnswerRepository {

    fun loadFavouritesQuestionsWithAnswer(): SharedFlow<List<QuestionWithAnswerEntity>>

    suspend fun addToFavouriteQuestionWithAnswer(
        questionWithAnswerEntity: QuestionWithAnswerEntity,
        isSavedByUser: Boolean
    )

    suspend fun deleteFromFavouriteQuestionWithAnswer(questionName: String)

}