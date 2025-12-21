package com.example.interviewaicoach.di

import com.example.interviewaicoach.data.local.QuestionsDataBase
import com.example.interviewaicoach.data.local.QuestionsWithAnswersDao
import com.example.interviewaicoach.data.local.reposiroties.FavouriteQuestionsWIthAnswerRepositoryImpl
import com.example.interviewaicoach.data.remote.ApiFactory
import com.example.interviewaicoach.data.remote.ApiService
import com.example.interviewaicoach.data.repositories.AnswersRepositoryImpl
import com.example.interviewaicoach.data.repositories.LoadQuestionRepositoryImpl
import com.example.interviewaicoach.data.repositories.RecordAudioRepositoryImpl
import com.example.interviewaicoach.data.repositories.SpeechToTextRepositoryImpl
import com.example.interviewaicoach.domain.repositories.AnswerRepository
import com.example.interviewaicoach.domain.repositories.FavouriteQuestionsWIthAnswerRepository
import com.example.interviewaicoach.domain.repositories.LoadQuestionRepository
import com.example.interviewaicoach.domain.repositories.RecordAudioRepository
import com.example.interviewaicoach.domain.repositories.SpeechToTextRepository
import com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases.AddQuestionWithAnswerToFavouriteUseCase
import com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases.DeleteQuestionWithAnswerFromFavouriteUseCase
import com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases.LoadFavouriteQuestionsWithAnswerUseCase
import com.example.interviewaicoach.domain.usecases.loadAnswersAndQuestionsUseCase.LoadCorrectAnswerUseCase
import com.example.interviewaicoach.domain.usecases.loadAnswersAndQuestionsUseCase.LoadQuestionUseCase
import com.example.interviewaicoach.domain.usecases.loadAnswersAndQuestionsUseCase.PostUserAnswerUseCase
import com.example.interviewaicoach.domain.usecases.recordSpeechUseCases.StartRecordingUseCase
import com.example.interviewaicoach.domain.usecases.recordSpeechUseCases.StopRecordingUseCase
import com.example.interviewaicoach.domain.usecases.recordSpeechUseCases.TranscribeAudioUseCase
import com.example.interviewaicoach.presentation.viemodels.DirectionScreenViewModel
import com.example.interviewaicoach.presentation.viemodels.GradeScreenViewModel
import com.example.interviewaicoach.presentation.viemodels.QuestionsWithAnswersViewModel
import com.example.interviewaicoach.presentation.viemodels.ResultScreenViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val appModule = module {

    single<ApiService> {
        ApiFactory.getApiService()
    }

    single<QuestionsWithAnswersDao> {
        QuestionsDataBase.getInstance(androidApplication()).getQuestionsWithAnswersDao()
    }

    single<AnswerRepository> {
        AnswersRepositoryImpl(apiService = get<ApiService>())
    }
    single<FavouriteQuestionsWIthAnswerRepository> {
        FavouriteQuestionsWIthAnswerRepositoryImpl(questionsWithAnswersDao = get<QuestionsWithAnswersDao>())
    }
    single<LoadQuestionRepository> {
        LoadQuestionRepositoryImpl(
            apiService = get<ApiService>(),
            questionsWithAnswersDao = get<QuestionsWithAnswersDao>()
        )
    }
    single<SpeechToTextRepository> {
        SpeechToTextRepositoryImpl(
            apiService = get<ApiService>()
        )
    }
    single<RecordAudioRepository> {
        RecordAudioRepositoryImpl()
    }

    factory<AddQuestionWithAnswerToFavouriteUseCase> {
        AddQuestionWithAnswerToFavouriteUseCase(
            favouriteQuestionsWIthAnswerRepository = get<FavouriteQuestionsWIthAnswerRepository>()
        )
    }

    factory<DeleteQuestionWithAnswerFromFavouriteUseCase> {
        DeleteQuestionWithAnswerFromFavouriteUseCase(
            favouriteQuestionsWIthAnswerRepository = get<FavouriteQuestionsWIthAnswerRepository>()
        )
    }
    factory<LoadFavouriteQuestionsWithAnswerUseCase> {
        LoadFavouriteQuestionsWithAnswerUseCase(
            favouriteQuestionsWIthAnswerRepository = get<FavouriteQuestionsWIthAnswerRepository>()
        )
    }
    factory<StartRecordingUseCase> {
        StartRecordingUseCase(
            recordAudioRepository = get<RecordAudioRepository>()
        )
    }

    factory<LoadCorrectAnswerUseCase> {
        LoadCorrectAnswerUseCase(
            answerRepository = get<AnswerRepository>()
        )
    }
    factory<LoadQuestionUseCase> {
        LoadQuestionUseCase(
            loadQuestionRepository = get<LoadQuestionRepository>()
        )
    }

    factory<PostUserAnswerUseCase> {
        PostUserAnswerUseCase(
            answerRepository = get<AnswerRepository>()
        )
    }
    factory<LoadFavouriteQuestionsWithAnswerUseCase> {
        LoadFavouriteQuestionsWithAnswerUseCase(
            favouriteQuestionsWIthAnswerRepository = get<FavouriteQuestionsWIthAnswerRepository>()
        )
    }

    factory<StopRecordingUseCase> {
        StopRecordingUseCase(
            recordAudioRepository = get<RecordAudioRepository>()
        )
    }
    factory<TranscribeAudioUseCase> {
        TranscribeAudioUseCase(
            repository = get<SpeechToTextRepository>()
        )
    }

    factory<DeleteQuestionWithAnswerFromFavouriteUseCase> {
        DeleteQuestionWithAnswerFromFavouriteUseCase(
            favouriteQuestionsWIthAnswerRepository = get<FavouriteQuestionsWIthAnswerRepository>()
        )
    }
    viewModel<QuestionsWithAnswersViewModel> { (directionInIt: String, gradeName: String) ->
        QuestionsWithAnswersViewModel(
            startRecordingUseCase = get<StartRecordingUseCase>(),
            stopRecordingUseCase = get<StopRecordingUseCase>(),
            transcribeAudioUseCase = get<TranscribeAudioUseCase>(),
            directionInIt = directionInIt,
            gradeName = gradeName,
            loadQuestionUseCase = get<LoadQuestionUseCase>(),
            loadCorrectAnswerUseCase = get<LoadCorrectAnswerUseCase>(),
            postUserAnswerUseCase = get<PostUserAnswerUseCase>(),
            addQuestionWithAnswerToFavouriteUseCase = get<AddQuestionWithAnswerToFavouriteUseCase>(),
            loadFavouriteQuestionsWithAnswerUseCase = get<LoadFavouriteQuestionsWithAnswerUseCase>(),
            deleteQuestionWithAnswerFromFavouriteUseCase = get<DeleteQuestionWithAnswerFromFavouriteUseCase>(),
        )
    }
    viewModel<ResultScreenViewModel> { (
                                           pointsSum: Int,
                                           countsOfQuestions: Int,
                                           numberOfSavedQuestions: Int,
                                           numberOfVoiceAnsweredQuestions: Int) ->
        ResultScreenViewModel(
            pointsSum,
            countsOfQuestions,
            numberOfSavedQuestions,
            numberOfVoiceAnsweredQuestions
        )

    }

    viewModel<DirectionScreenViewModel> {
        DirectionScreenViewModel()
    }

    viewModel<GradeScreenViewModel> {
        GradeScreenViewModel()
    }
}

fun paramsForQuestionWithAnswerViewModel(directionInIt: String, gradeName: String) =
    parametersOf(directionInIt, gradeName)

fun paramsForResultScreeViewModel(
    pointsSum: Int,
    countsOfQuestions: Int,
    numberOfSavedQuestions: Int,
    numberOfVoiceAnsweredQuestions: Int,
) = parametersOf(
    pointsSum,
    countsOfQuestions,
    numberOfSavedQuestions,
    numberOfVoiceAnsweredQuestions
)

