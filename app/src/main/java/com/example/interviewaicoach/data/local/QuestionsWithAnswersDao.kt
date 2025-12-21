package com.example.interviewaicoach.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.interviewaicoach.data.local.dbModels.QuestionWithAnswerDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionsWithAnswersDao {

    @Query("SELECT questionContent FROM QuestionWithAnswerDbModel WHERE isSavedByUser = 0 AND categoryName = :categoryName AND gradeName =:gradeName ")
    fun getAllOnlySystemSavedQuestionsContentFromDb(categoryName: String, gradeName: String ): List<String>

    @Query("SELECT * FROM QuestionWithAnswerDbModel WHERE isSavedByUser = 1")
    fun getAllOnlyUserSavedQuestionsWithAnswersFromDb(): Flow<List<QuestionWithAnswerDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestionWithAnswerToDb(questionWithAnswerDbModel: QuestionWithAnswerDbModel)

    @Query("DELETE FROM QuestionWithAnswerDbModel WHERE questionContent = :questionName")
    suspend fun deleteQuestionWithAnswerFromDb(questionName: String)

}