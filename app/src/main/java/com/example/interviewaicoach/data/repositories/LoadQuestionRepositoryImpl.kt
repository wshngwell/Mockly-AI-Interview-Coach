package com.example.interviewaicoach.data.repositories

import com.example.interviewaicoach.data.local.QuestionsWithAnswersDao
import com.example.interviewaicoach.data.mappers.mapCategoryWithGradeToChatRequestForQuestion
import com.example.interviewaicoach.data.mappers.mapToQuestionEntity
import com.example.interviewaicoach.data.remote.ApiService
import com.example.interviewaicoach.data.remote.parseToLoadingException
import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.domain.entities.TResult
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.GradeEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.repositories.LoadQuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadQuestionRepositoryImpl(
    private val apiService: ApiService,
    private val questionsWithAnswersDao: QuestionsWithAnswersDao
) : LoadQuestionRepository {

    override suspend fun loadQuestion(
        categoryName: String,
        gradeEntity: GradeEntity,
    ): TResult<QuestionEntity, LoadingException> =
        withContext(Dispatchers.IO) {

            return@withContext runCatching {

                val savedBySystem =
                    questionsWithAnswersDao.getAllOnlySystemSavedQuestionsContentFromDb()
                        .takeLast(150)

                val result = apiService.sendPromt(
                    mapCategoryWithGradeToChatRequestForQuestion(
                        categoryName,
                        gradeEntity,
                        savedBySystem
                    )
                ).mapToQuestionEntity(categoryName, gradeEntity)

                TResult.Success<QuestionEntity, LoadingException>(data = result)

            }.getOrElse {
                TResult.Error(it.parseToLoadingException())
            }
        }

}