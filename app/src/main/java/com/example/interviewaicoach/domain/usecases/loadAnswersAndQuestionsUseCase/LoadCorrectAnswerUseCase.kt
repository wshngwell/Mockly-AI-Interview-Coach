package com.example.interviewaicoach.domain.usecases.loadAnswersAndQuestionsUseCase

import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.repositories.AnswerRepository

class LoadCorrectAnswerUseCase(
    private val answerRepository: AnswerRepository
) {
    operator fun invoke(questionEntity: QuestionEntity) =
        answerRepository.loadCorrectAnswer(questionEntity)
}