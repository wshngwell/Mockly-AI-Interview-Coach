package com.example.interviewaicoach.domain.repositories

import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import kotlinx.coroutines.flow.SharedFlow

interface FavouriteQuestionsWIthAnswerRepository {

    val loadFavouritesQuestions: SharedFlow<List<QuestionEntity>>

    suspend fun addToFavouriteQuestion(
        questionEntity: QuestionEntity,
        isSavedByUser: Boolean
    )

    val loadCategoriesNamesFromFavouriteQuestions: SharedFlow<List<String>>

    suspend fun deleteFromFavouriteQuestion(questionName: String)

}