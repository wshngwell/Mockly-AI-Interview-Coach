package com.example.interviewaicoach.data.repositories

import com.example.interviewaicoach.data.mappers.mapQuestionEntityToChatRequestForAnswerRating
import com.example.interviewaicoach.data.mappers.mapQuestionEntityToChatRequestForCorrectAnswer
import com.example.interviewaicoach.data.mappers.mapToAnswerEntity
import com.example.interviewaicoach.data.mappers.mapToAnswerRating
import com.example.interviewaicoach.data.remote.ApiService
import com.example.interviewaicoach.data.remote.parseToLoadingException
import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.domain.entities.TResult
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerRating
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.repositories.AnswerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnswersRepositoryImpl(
    private val apiService: ApiService
) : AnswerRepository {

    override suspend fun loadCorrectAnswer(questionEntity: QuestionEntity):
            TResult<AnswerEntity, LoadingException> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            val result = apiService.sendPromt(
                request = mapQuestionEntityToChatRequestForCorrectAnswer(questionEntity)
            ).mapToAnswerEntity()
            TResult.Success<AnswerEntity, LoadingException>(data = result)
        }.getOrElse {
            TResult.Error(exception = it.parseToLoadingException())
        }
    }

    override suspend fun postUserAnswer(
        questionEntity: QuestionEntity,
        answerEntity: AnswerEntity
    ): TResult<AnswerRating, LoadingException> =
        withContext(
            Dispatchers.IO
        ) {
            return@withContext runCatching {
                val result = apiService.sendPromt(
                    request = mapQuestionEntityToChatRequestForAnswerRating(
                        questionEntity,
                        answerEntity
                    )
                ).mapToAnswerRating()
                TResult.Success<AnswerRating, LoadingException>(data = result)
            }.getOrElse {
                TResult.Error(exception = it.parseToLoadingException())
            }

        }
}