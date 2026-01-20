package com.example.interviewaicoach.data.repositories

import com.example.interviewaicoach.data.mappers.mapQuestionEntityToChatRequestForAnswerRating
import com.example.interviewaicoach.data.mappers.mapQuestionEntityToChatRequestForCorrectAnswer
import com.example.interviewaicoach.data.mappers.mapToAiFeedback
import com.example.interviewaicoach.data.mappers.mapToAnswerEntity
import com.example.interviewaicoach.data.remote.ApiService
import com.example.interviewaicoach.data.remote.dto.dtoForText.ChatStreamChunkDto
import com.example.interviewaicoach.data.remote.parseToLoadingException
import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.domain.entities.TResult
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerAiFeedbackEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.CorrectAnswerEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.repositories.AnswerRepository
import com.example.interviewaicoach.presentation.GsonUtil.fromJson
import com.example.interviewaicoach.utils.myLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn

class AnswersRepositoryImpl(
    private val apiService: ApiService
) : AnswerRepository {

    override fun loadCorrectAnswer(questionEntity: QuestionEntity):
            Flow<TResult<CorrectAnswerEntity, LoadingException>> = channelFlow {
        runCatching {
            val responseBody = apiService.sendPromt(
                request = mapQuestionEntityToChatRequestForCorrectAnswer(questionEntity)
            )
            val fullContentAccumulator = StringBuilder()

            responseBody.byteStream().bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val content = retrievingContentFromString(line)
                    if (content.isNotEmpty()) {
                        fullContentAccumulator.append(content)

                        val answerEntity = fullContentAccumulator.toString().mapToAnswerEntity()

                        send(TResult.Success<CorrectAnswerEntity, LoadingException>(data = answerEntity))
                    }
                }
            }

        }.getOrElse {
            send(TResult.Error(exception = it.parseToLoadingException()))
        }
    }
        .flatMapConcat {
            channelFlow {
                delay(50)
                send(it)
            }
        }
        .flowOn(Dispatchers.IO)

    fun retrievingContentFromString(line: String): String {
        if (line.isBlank() || line.startsWith(":") || line.contains("[DONE]")) return ""

        return if (line.startsWith("data: ") && !line.contains("[DONE]")) {
            val jsonString = line.removePrefix("data: ")

            val chunk = jsonString.fromJson<ChatStreamChunkDto>()
            chunk.choices[0].delta.content ?: ""
        } else ""
    }

    override fun postUserAnswer(
        questionEntity: QuestionEntity,
        userAnswer: String,
        correctAnswerEntity: CorrectAnswerEntity
    ): Flow<TResult<AnswerAiFeedbackEntity, LoadingException>> = channelFlow {
        runCatching {

            val responseBody = apiService.sendPromt(
                request = mapQuestionEntityToChatRequestForAnswerRating(
                    questionEntity,
                    userAnswer,
                    correctAnswerEntity
                )
            )
            val fullContentAccumulator = StringBuilder()

            responseBody.byteStream().bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val content = retrievingContentFromString(line)
                    if (content.isNotEmpty()) {
                        fullContentAccumulator.append(content)

                        val aiFeedBack =
                            fullContentAccumulator.toString().mapToAiFeedback()
                        myLog(aiFeedBack.toString())
                        send(TResult.Success<AnswerAiFeedbackEntity, LoadingException>(data = aiFeedBack))
                    }
                }
            }
        }.getOrElse {
            send(TResult.Error(exception = it.parseToLoadingException()))
        }
    }
        .flatMapConcat {
            channelFlow {
                send(it)
                delay(50)
            }
        }
        .flowOn(Dispatchers.IO)
}