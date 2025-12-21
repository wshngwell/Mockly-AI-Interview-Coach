package com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases

import com.example.interviewaicoach.domain.repositories.FavouriteQuestionsWIthAnswerRepository

class LoadFavouriteQuestionsWithAnswerUseCase(
    private val favouriteQuestionsWIthAnswerRepository: FavouriteQuestionsWIthAnswerRepository
) {
    operator fun invoke() =
        favouriteQuestionsWIthAnswerRepository.loadFavouritesQuestionsWithAnswer()

}