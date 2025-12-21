package com.example.interviewaicoach.domain.usecases.loadAnswersAndQuestionsUseCase

import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.CorrectAnswerEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.repositories.AnswerRepository

class PostUserAnswerUseCase(
    private val answerRepository: AnswerRepository
) {
    operator fun invoke(
        questionEntity: QuestionEntity,
        userAnswer: String,
        correctAnswerEntity: CorrectAnswerEntity
    ) = answerRepository.postUserAnswer(questionEntity, userAnswer, correctAnswerEntity)
}