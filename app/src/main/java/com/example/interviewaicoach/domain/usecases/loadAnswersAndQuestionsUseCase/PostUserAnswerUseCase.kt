package com.example.interviewaicoach.domain.usecases.loadAnswersAndQuestionsUseCase

import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.repositories.AnswerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostUserAnswerUseCase(
    private val answerRepository: AnswerRepository
) {
    suspend operator fun invoke(
        questionEntity: QuestionEntity,
        answerEntity: AnswerEntity
    ) = withContext(Dispatchers.IO) {
        answerRepository.postUserAnswer(questionEntity, answerEntity)
    }

}