package com.example.interviewaicoach.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.interviewaicoach.data.local.dbModels.QuestionDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionsDao {

    @Query("SELECT questionContent FROM QuestionDbModel WHERE isSavedByUser = 0 AND categoryName = :categoryName AND gradeName =:gradeName ")
    fun getAllOnlySystemSavedQuestionsContentFromDb(
        categoryName: String,
        gradeName: String
    ): List<String>

    @Query("SELECT DISTINCT categoryName FROM QuestionDbModel")
    fun getAllCategoriesFromDb(): Flow<List<String>>

    @Query("SELECT DISTINCT * FROM QuestionDbModel WHERE isSavedByUser = 1")
    fun getAllOnlyUserSavedQuestionsFromDb(): Flow<List<QuestionDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestionToDb(questionDbModel: QuestionDbModel)

    @Query("DELETE FROM QuestionDbModel WHERE questionContent = :questionName")
    suspend fun deleteQuestionFromDb(questionName: String)

}