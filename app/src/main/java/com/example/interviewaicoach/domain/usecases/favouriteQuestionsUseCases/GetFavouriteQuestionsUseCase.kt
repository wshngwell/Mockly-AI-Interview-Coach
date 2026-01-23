package com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases

import com.example.interviewaicoach.domain.repositories.FavouriteQuestionsWIthAnswerRepository

class GetFavouriteQuestionsUseCase(
    private val favouriteQuestionsWIthAnswerRepository: FavouriteQuestionsWIthAnswerRepository
) {
    operator fun invoke() =
        favouriteQuestionsWIthAnswerRepository.loadFavouritesQuestions

}