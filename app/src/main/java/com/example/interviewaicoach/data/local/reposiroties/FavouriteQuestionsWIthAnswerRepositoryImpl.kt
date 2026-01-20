package com.example.interviewaicoach.data.local.reposiroties

import com.example.interviewaicoach.data.local.QuestionsDao
import com.example.interviewaicoach.data.mappers.mapToQuestionEntity
import com.example.interviewaicoach.data.mappers.toQuestionDbModel
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.repositories.FavouriteQuestionsWIthAnswerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext

class FavouriteQuestionsWIthAnswerRepositoryImpl(
    private val questionsDao: QuestionsDao
) : FavouriteQuestionsWIthAnswerRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    override val loadFavouritesQuestions: SharedFlow<List<QuestionEntity>> =
        questionsDao.getAllOnlyUserSavedQuestionsFromDb()
            .map { listOfQuestions ->
                listOfQuestions.map {
                    it.mapToQuestionEntity()
                }
            }
            .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
            .distinctUntilChanged()
            .shareIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(
                    stopTimeoutMillis = 10_000,
                    replayExpirationMillis = 0
                ),
                replay = 1
            )

    override val loadCategoriesNamesFromFavouriteQuestions: SharedFlow<List<String>> =
        questionsDao.getAllCategoriesFromDb()
            .map { listOfQuestionsWithAnswers ->
                listOfQuestionsWithAnswers.map {
                    it
                }
            }
            .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
            .distinctUntilChanged()
            .shareIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(
                    stopTimeoutMillis = 10_000,
                    replayExpirationMillis = 0
                ),
                replay = 1
            )

    override suspend fun addToFavouriteQuestion(
        questionEntity: QuestionEntity,
        isSavedByUser: Boolean,
    ) =
        withContext(Dispatchers.IO) {
            questionsDao.addQuestionToDb(
                questionEntity.toQuestionDbModel(isSavedByUser)
            )
        }

    override suspend fun deleteFromFavouriteQuestion(questionName: String) =
        withContext(Dispatchers.IO) {
            questionsDao.deleteQuestionFromDb(questionName)
        }
}