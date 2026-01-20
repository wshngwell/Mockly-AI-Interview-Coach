package com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases

import com.example.interviewaicoach.domain.repositories.FavouriteQuestionsWIthAnswerRepository

class GetFavouriteCategoriesUseCase(
    private val favouriteQuestionsWIthAnswerRepository: FavouriteQuestionsWIthAnswerRepository
) {
    operator fun invoke() =
        favouriteQuestionsWIthAnswerRepository.loadCategoriesNamesFromFavouriteQuestions
}