package com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases

import com.example.interviewaicoach.domain.repositories.FavouriteQuestionsWIthAnswerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteQuestionFromFavouriteUseCase(
    private val favouriteQuestionsWIthAnswerRepository: FavouriteQuestionsWIthAnswerRepository
) {
    suspend operator fun invoke(questionName: String) = withContext(Dispatchers.IO) {
        favouriteQuestionsWIthAnswerRepository.deleteFromFavouriteQuestion(
            questionName
        )
    }

}