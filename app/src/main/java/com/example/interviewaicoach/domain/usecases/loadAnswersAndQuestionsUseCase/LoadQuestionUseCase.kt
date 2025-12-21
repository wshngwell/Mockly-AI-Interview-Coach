package com.example.interviewaicoach.domain.usecases.loadAnswersAndQuestionsUseCase

import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.GradeEntity
import com.example.interviewaicoach.domain.repositories.LoadQuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadQuestionUseCase(
    private val loadQuestionRepository: LoadQuestionRepository
) {
    suspend operator fun invoke(
        categoryName: String,
        gradeEntity: GradeEntity,
    ) = withContext(Dispatchers.IO) {
        loadQuestionRepository.loadQuestion(
            categoryName,
            gradeEntity,
        )
    }
}