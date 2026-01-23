package com.example.interviewaicoach.domain.usecases.loadAnswersAndQuestionsUseCase

import com.example.interviewaicoach.domain.repositories.LoadQuestionRepository

class LoadQuestionUseCase(
    private val loadQuestionRepository: LoadQuestionRepository
) {
    operator fun invoke(
        categoryName: String,
        gradeName: String,
    ) =
        loadQuestionRepository.loadQuestion(
            categoryName,
            gradeName,
        )
}
