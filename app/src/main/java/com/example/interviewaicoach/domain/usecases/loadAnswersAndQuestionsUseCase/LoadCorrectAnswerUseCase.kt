package com.example.interviewaicoach.domain.usecases.loadAnswersAndQuestionsUseCase

import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.repositories.AnswerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadCorrectAnswerUseCase(
    private val answerRepository: AnswerRepository
) {
    suspend operator fun invoke(questionEntity: QuestionEntity) = withContext(Dispatchers.IO) {
        answerRepository.loadCorrectAnswer(questionEntity)
    }
}