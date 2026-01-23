package com.example.interviewaicoach.data.local.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QuestionDbModel")
data class QuestionDbModel(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val questionContent: String,
    val questionTopic: String,
    val categoryName: String,
    val gradeName: String,
    val isSavedByUser: Boolean

)
