package com.example.interviewaicoach.data.local.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QuestionWithAnswerDbModel")
data class QuestionWithAnswerDbModel(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val questionContent: String,
    val answerContent: String,
    val categoryName: String,
    val gradeDbModel: GradeDbModel,
    val isSavedByUser: Boolean

)
