package com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases

import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionWithAnswerEntity
import com.example.interviewaicoach.domain.repositories.FavouriteQuestionsWIthAnswerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddQuestionWithAnswerToFavouriteUseCase(
    private val favouriteQuestionsWIthAnswerRepository: FavouriteQuestionsWIthAnswerRepository
) {
    suspend operator fun invoke(questionWithAnswerEntity: QuestionWithAnswerEntity) =
        withContext(Dispatchers.IO) {
            favouriteQuestionsWIthAnswerRepository.addToFavouriteQuestionWithAnswer(
                questionWithAnswerEntity
            )
        }
}