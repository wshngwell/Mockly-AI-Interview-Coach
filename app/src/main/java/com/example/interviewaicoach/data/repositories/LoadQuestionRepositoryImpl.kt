package com.example.interviewaicoach.data.repositories

import com.example.interviewaicoach.data.local.QuestionsDao
import com.example.interviewaicoach.data.mappers.mapCategoryWithGradeToChatRequestForQuestion
import com.example.interviewaicoach.data.mappers.mapToQuestionEntity
import com.example.interviewaicoach.data.mappers.retrievingContentFromString
import com.example.interviewaicoach.data.remote.ApiService
import com.example.interviewaicoach.data.remote.parseToLoadingException
import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.domain.entities.TResult
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.repositories.LoadQuestionRepository
import com.example.interviewaicoach.utils.myLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn

class LoadQuestionRepositoryImpl(
    private val apiService: ApiService,
    private val questionsDao: QuestionsDao
) : LoadQuestionRepository {

    override fun loadQuestion(
        categoryName: String,
        gradeName: String,
    ): Flow<TResult<QuestionEntity, LoadingException>> = channelFlow {
        runCatching {

            val savedBySystem =
                questionsDao.getAllOnlySystemSavedQuestionsContentFromDb(categoryName, gradeName)
                    .takeLast(150)

            val responseBody = apiService.sendPromt(
                mapCategoryWithGradeToChatRequestForQuestion(
                    categoryName,
                    gradeName,
                    savedBySystem
                )
            )
            val fullContentAccumulator = StringBuilder()


            responseBody.byteStream().bufferedReader().useLines { lines ->
                lines.forEach { line ->

                    val content = retrievingContentFromString(line)

                    if (content.isNotEmpty()) {
                        fullContentAccumulator.append(content)

                        val questionEntity =
                            fullContentAccumulator.toString().mapToQuestionEntity(
                                categoryName,
                                gradeName
                            )
                        myLog(questionEntity.toString())
                        send(TResult.Success<QuestionEntity, LoadingException>(data = questionEntity))
                    }
                }
            }

        }.getOrElse {
            send(TResult.Error(it.parseToLoadingException()))
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