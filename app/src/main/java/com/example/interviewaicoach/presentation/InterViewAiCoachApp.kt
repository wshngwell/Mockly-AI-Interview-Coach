package com.example.interviewaicoach.presentation

import android.app.Application
import com.example.interviewaicoach.data.local.QuestionsDataBase
import com.example.interviewaicoach.data.remote.ApiFactory
import com.example.interviewaicoach.data.repositories.AnswersRepositoryImpl
import com.example.interviewaicoach.data.repositories.LoadQuestionRepositoryImpl
import com.example.interviewaicoach.domain.entities.TResult
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.GradeEntity
import com.example.interviewaicoach.domain.usecases.loadAnswersAndQuestionsUseCase.LoadCorrectAnswerUseCase
import com.example.interviewaicoach.domain.usecases.loadAnswersAndQuestionsUseCase.LoadQuestionUseCase
import com.example.interviewaicoach.utils.myLog
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class InterViewAiCoachApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val apiService = ApiFactory.getApiService()
        val questionsDao = QuestionsDataBase.getInstance(this).getQuestionsWithAnswersDao()

        MainScope().launch {
            val loadQuestionUseCase = LoadQuestionUseCase(
                LoadQuestionRepositoryImpl(
                    apiService,
                    questionsDao
                )
            )(categoryName = "Android", gradeEntity = GradeEntity.MIDDLE)

            when (loadQuestionUseCase) {
                is TResult.Error -> myLog(loadQuestionUseCase.exception.message!!)
                is TResult.Success -> {
                    myLog(loadQuestionUseCase.data.toString())

                    val result = LoadCorrectAnswerUseCase(
                        AnswersRepositoryImpl(
                            apiService = apiService
                        )
                    )(questionEntity = loadQuestionUseCase.data)

                    when (result) {
                        is TResult.Error -> {

                        }

                        is TResult.Success -> {
                            myLog(result.data.toString())
                        }
                    }

                }

            }


        }


    }
}