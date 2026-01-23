package com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases

import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.repositories.FavouriteQuestionsWIthAnswerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddQuestionToFavouriteUseCase(
    private val favouriteQuestionsWIthAnswerRepository: FavouriteQuestionsWIthAnswerRepository
) {
    suspend operator fun invoke(questionEntity: QuestionEntity, isSavedByUser: Boolean) =
        withContext(Dispatchers.IO) {
            favouriteQuestionsWIthAnswerRepository.addToFavouriteQuestion(
                questionEntity,
                isSavedByUser
            )
        }
}