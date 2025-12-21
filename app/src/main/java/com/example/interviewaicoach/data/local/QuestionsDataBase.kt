package com.example.interviewaicoach.data.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.interviewaicoach.data.local.dbModels.QuestionWithAnswerDbModel

@Database(
    entities = [QuestionWithAnswerDbModel::class],
    version = 2,
    exportSchema = true
)
abstract class QuestionsDataBase : RoomDatabase() {

    abstract fun getQuestionsWithAnswersDao(): QuestionsWithAnswersDao

    companion object {
        private const val DB_NAME = "QuestionsDataBase"

        private var INSTANCE: QuestionsDataBase? = null

        private val LOCK = Any()

        fun getInstance(application: Application): QuestionsDataBase {

            INSTANCE?.let { return it }
            synchronized(LOCK) {
                INSTANCE?.let { return it }
                val questionsDataBase = Room.databaseBuilder(
                    context = application,
                    klass = QuestionsDataBase::class.java,
                    name = DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = questionsDataBase
                return questionsDataBase
            }
        }
    }
}