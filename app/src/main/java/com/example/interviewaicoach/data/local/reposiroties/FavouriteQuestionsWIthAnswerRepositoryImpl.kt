package com.example.interviewaicoach.data.local.reposiroties

import com.example.interviewaicoach.data.local.QuestionsWithAnswersDao
import com.example.interviewaicoach.data.mappers.mapToQuestionWithAnswerEntity
import com.example.interviewaicoach.data.mappers.toQuestionWithAnswerDbModel
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionWithAnswerEntity
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
    private val questionsWithAnswersDao: QuestionsWithAnswersDao
) : FavouriteQuestionsWIthAnswerRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun loadFavouritesQuestionsWithAnswer(): SharedFlow<List<QuestionWithAnswerEntity>> =
        questionsWithAnswersDao.getAllOnlyUserSavedQuestionsWithAnswersFromDb()
            .map { listOfQuestionsWithAnswers ->
                listOfQuestionsWithAnswers.map {
                    it.mapToQuestionWithAnswerEntity()
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

    override suspend fun addToFavouriteQuestionWithAnswer(
        questionWithAnswerEntity: QuestionWithAnswerEntity,
        isSavedByUser: Boolean,
    ) =
        withContext(Dispatchers.IO) {
            questionsWithAnswersDao.addQuestionWithAnswerToDb(
                questionWithAnswerEntity.toQuestionWithAnswerDbModel(isSavedByUser)
            )
        }

    override suspend fun deleteFromFavouriteQuestionWithAnswer(questionName: String) =
        withContext(Dispatchers.IO) {
            questionsWithAnswersDao.deleteQuestionWithAnswerFromDb(questionName)
        }
}