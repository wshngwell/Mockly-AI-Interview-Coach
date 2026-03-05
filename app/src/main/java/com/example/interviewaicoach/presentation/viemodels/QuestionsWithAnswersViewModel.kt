package com.example.interviewaicoach.presentation.viemodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewaicoach.domain.SingleFlowEvent
import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.domain.entities.LoadingException.SpeechRecordingError
import com.example.interviewaicoach.domain.entities.TResult
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerAiFeedbackEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.CorrectAnswerEntity
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases.AddQuestionToFavouriteUseCase
import com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases.DeleteQuestionFromFavouriteUseCase
import com.example.interviewaicoach.domain.usecases.favouriteQuestionsUseCases.GetFavouriteQuestionsUseCase
import com.example.interviewaicoach.domain.usecases.loadAnswersAndQuestionsUseCase.LoadCorrectAnswerUseCase
import com.example.interviewaicoach.domain.usecases.loadAnswersAndQuestionsUseCase.LoadQuestionUseCase
import com.example.interviewaicoach.domain.usecases.loadAnswersAndQuestionsUseCase.PostUserAnswerUseCase
import com.example.interviewaicoach.domain.usecases.recordSpeechUseCases.StartRecordingUseCase
import com.example.interviewaicoach.domain.usecases.recordSpeechUseCases.StopRecordingUseCase
import com.example.interviewaicoach.domain.usecases.recordSpeechUseCases.TranscribeAudioUseCase
import com.example.interviewaicoach.presentation.viemodels.QuestionsWithAnswersViewModel.Event.NavigateToResultsScreen
import com.example.interviewaicoach.presentation.viemodels.QuestionsWithAnswersViewModel.State.ErrorType
import com.example.interviewaicoach.presentation.utils.myLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class QuestionsWithAnswersViewModel(
    private val directionInIt: String,
    private val gradeName: String,
    private val answeringFromFavouriteMode: Boolean,
    private val questionEntity: QuestionEntity?,
    private val startRecordingUseCase: StartRecordingUseCase,
    private val stopRecordingUseCase: StopRecordingUseCase,
    private val transcribeAudioUseCase: TranscribeAudioUseCase,
    private val loadQuestionUseCase: LoadQuestionUseCase,
    private val loadCorrectAnswerUseCase: LoadCorrectAnswerUseCase,
    private val postUserAnswerUseCase: PostUserAnswerUseCase,
    private val addQuestionToFavouriteUseCase: AddQuestionToFavouriteUseCase,
    private val getFavouriteQuestionsUseCase: GetFavouriteQuestionsUseCase,
    private val deleteQuestionFromFavouriteUseCase: DeleteQuestionFromFavouriteUseCase
) : ViewModel() {

    private var answeredJob: Job = Job()

    private var timerJob: Job = Job()

    data class State(

        val aiFeedBack: AnswerAiFeedbackEntity = AnswerAiFeedbackEntity(
            ratingFromAi = 0,
            goodPartAnswer = listOf(),
            badPartAnswer = listOf()
        ),
        val userAnswerText: String = "",
        val isRecording: Boolean = false,

        val answeringFromFavouriteMode: Boolean,

        val correctAnswerText: String = "",
        val questionText: String = "",
        val topicOfQuestion: String = "",
        val grade: String = "",
        val isQuestionVisible: Boolean = false,
        val isAnswerVisible: Boolean = false,
        val isAiFeedbackVisible: Boolean = false,
        val isCorrectAnswerLoading: Boolean = false,
        val isAiFeedbackLoading: Boolean = false,
        val isQuestionLoading: Boolean = false,
        val directionInIt: String = "",
        val favouriteAnswersAndQuestions: List<QuestionEntity> = listOf(),
        val isQuestionFavourite: Boolean = false,
        val currentNumberOfQuestion: Int = 0,

        val errorCause: LoadingException? = null,
        val errorType: ErrorType? = null,

        val shouldBeAiFeedbackBeShown: Boolean = false,
        val shouldBeCorrectAnswerBeShown: Boolean = false,

        val pointsSum: Int = 0,
        val numberOfLearnedQuestions: Int = 0,
        val numberOfSavedQuestions: Int = 0,
        val numberOfVoiceAnsweredQuestions: Int = 0,
        val recordingTime: Long = 0L,
    ) {
        sealed interface ErrorType {
            data object LoadingQuestionError : ErrorType
            data object LoadingCorrectAnswerError : ErrorType
            data object LoadingAiFeedbackError : ErrorType
            data object RecordingVoiceError : ErrorType
        }
    }

    private val _state = MutableStateFlow(
        State(
            directionInIt = directionInIt,
            grade = gradeName,
            answeringFromFavouriteMode = answeringFromFavouriteMode,
            questionText = questionEntity?.questionContent ?: "",
            topicOfQuestion = questionEntity?.questionTopic ?: "",
        )
    )

    val state = _state.asStateFlow()
    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow


    sealed interface Intent {

        data object AddOrDeleteToFavourite : Intent

        data object LoadNextQuestion : Intent
        data object BackToFavouriteScreen : Intent
        data object SendUserAnswer : Intent
        data object ShowCorrectAnswer : Intent
        data object StartRecording : Intent
        data object EndInterview : Intent
        data object AnswerAgain : Intent
        data object Retry : Intent
    }

    sealed interface Event {
        data class NavigateToResultsScreen(
            val directionInIt: String,
            val gradeName: String,
            val pointsSum: Int,
            val countsOfQuestions: Int,
            val numberOfSavedQuestions: Int,
            val numberOfVoiceAnsweredQuestions: Int
        ) : Event

        data object BackToFavouriteScreen : Event

    }

    fun sendIntent(intent: Intent) {
        when (intent) {

            Intent.LoadNextQuestion -> {
                _state.update {
                    it.copy(
                        topicOfQuestion = "",
                        questionText = "",
                        correctAnswerText = "",
                        shouldBeCorrectAnswerBeShown = false,
                        shouldBeAiFeedbackBeShown = false,
                        isQuestionFavourite = false,
                        userAnswerText = "",
                        aiFeedBack = AnswerAiFeedbackEntity(
                            ratingFromAi = 0,
                            goodPartAnswer = listOf(),
                            badPartAnswer = listOf()
                        )
                    )
                }
                loadingQuestionWithAnswer()
            }

            Intent.AddOrDeleteToFavourite -> {

                viewModelScope.launch {
                    val questionName =
                        state.value.favouriteAnswersAndQuestions.map { it.questionContent }
                    if (!questionName.contains(state.value.questionText)) {
                        addQuestionToFavourite(true)
                        _state.update {
                            it.copy(
                                numberOfSavedQuestions = it.numberOfSavedQuestions + 1
                            )
                        }
                    } else {
                        deleteQuestionFromFavouriteUseCase(
                            listOf(state.value.questionText)
                        )
                        _state.update {
                            it.copy(
                                numberOfSavedQuestions = it.numberOfSavedQuestions - 1
                            )
                        }
                    }
                }
            }

            Intent.StartRecording -> {


                viewModelScope.launch {

                    if (_state.value.isRecording) return@launch

                    _state.update { it.copy(isRecording = true) }

                    startTimerLogic()

                    when (val result = startRecordingUseCase()) {
                        is TResult.Error -> {

                            val errorType =
                                if (state.value.errorType == ErrorType.LoadingCorrectAnswerError) {
                                    ErrorType.LoadingCorrectAnswerError
                                } else ErrorType.RecordingVoiceError

                            stopTimerLogic()

                            _state.update {
                                it.copy(
                                    errorCause = result.exception,
                                    isRecording = false,
                                    errorType = errorType,
                                )
                            }

                            return@launch
                        }

                        is TResult.Success -> {

                            stopTimerLogic()

                            _state.update {
                                it.copy(
                                    isRecording = false,
                                    isAiFeedbackLoading = true,
                                    isAiFeedbackVisible = false,
                                    shouldBeAiFeedbackBeShown = true,
                                    shouldBeCorrectAnswerBeShown = true,
                                )
                            }
                            myLog("Ответ в байтах: ${result.data}")

                            if (state.value.errorType != null) {
                                _state.update {
                                    it.copy(
                                        isAiFeedbackLoading = false,
                                        isAiFeedbackVisible = false,
                                        shouldBeAiFeedbackBeShown = false,
                                        shouldBeCorrectAnswerBeShown = false,
                                    )
                                }
                                return@launch
                            }

                            when (val transcribeResult = transcribeAudioUseCase(result.data)) {

                                is TResult.Error -> {
                                    val errorType =
                                        if (state.value.errorType == ErrorType.LoadingCorrectAnswerError)
                                            ErrorType.LoadingCorrectAnswerError
                                        else ErrorType.RecordingVoiceError

                                    _state.update {
                                        it.copy(
                                            errorCause = transcribeResult.exception,
                                            errorType = errorType,
                                            isAiFeedbackLoading = false,
                                            isAiFeedbackVisible = false,
                                            shouldBeAiFeedbackBeShown = false,
                                            shouldBeCorrectAnswerBeShown = false,
                                        )
                                    }
                                    return@launch
                                }

                                is TResult.Success -> {
                                    if (transcribeResult.data.resultString.isEmpty() || transcribeResult.data.resultString.none { it.isLetter() }) {

                                        _state.update {
                                            it.copy(
                                                errorCause = SpeechRecordingError(),
                                                errorType = ErrorType.RecordingVoiceError,
                                                isAiFeedbackLoading = false,
                                                isAiFeedbackVisible = false,
                                                shouldBeAiFeedbackBeShown = false,
                                                shouldBeCorrectAnswerBeShown = false,
                                            )
                                        }
                                        return@launch
                                    }
                                    _state.update {
                                        it.copy(
                                            userAnswerText = transcribeResult.data.resultString,
                                        )
                                    }
                                    myLog("Ответ в тексте: ${_state.value.userAnswerText}")
                                }
                            }
                            launch(Dispatchers.IO) {
                                answeredJob.join()
                                if (state.value.errorCause != null) return@launch
                                loadAiFeedback()
                            }
                        }
                    }
                }
            }

            Intent.ShowCorrectAnswer -> {
                _state.update {
                    it.copy(
                        shouldBeCorrectAnswerBeShown = true,
                        numberOfLearnedQuestions = it.numberOfLearnedQuestions + 1
                    )
                }
            }

            Intent.SendUserAnswer -> {

                stopTimerLogic()

                stopRecordingUseCase()
            }

            Intent.EndInterview -> {

                if (state.value.isCorrectAnswerLoading && state.value.shouldBeCorrectAnswerBeShown && !state.value.shouldBeAiFeedbackBeShown) {
                    _state.update { it.copy(numberOfLearnedQuestions = it.numberOfLearnedQuestions - 1) }
                }

                _event.emit(
                    NavigateToResultsScreen(
                        directionInIt = state.value.directionInIt,
                        gradeName = state.value.grade,
                        pointsSum = state.value.pointsSum,
                        countsOfQuestions = state.value.numberOfLearnedQuestions,
                        numberOfSavedQuestions = state.value.numberOfSavedQuestions,
                        numberOfVoiceAnsweredQuestions = state.value.numberOfVoiceAnsweredQuestions
                    )
                )
            }

            Intent.Retry -> {

                val currentErrorType = state.value.errorType

                _state.update {
                    it.copy(
                        errorType = null,
                        errorCause = null
                    )
                }

                currentErrorType?.let {
                    when (it) {
                        ErrorType.LoadingAiFeedbackError -> {
                            viewModelScope.launch {
                                loadAiFeedback()
                            }
                        }

                        ErrorType.LoadingCorrectAnswerError -> viewModelScope.launch {
                            answeredJob.cancel()

                            answeredJob = viewModelScope.launch {
                                loadAnswer()
                            }
                        }

                        ErrorType.LoadingQuestionError -> {
                            loadingQuestionWithAnswer()
                        }

                        ErrorType.RecordingVoiceError -> sendIntent(Intent.StartRecording)
                    }
                }

            }

            Intent.AnswerAgain -> {
                _state.update {
                    it.copy(
                        shouldBeCorrectAnswerBeShown = false,
                        shouldBeAiFeedbackBeShown = false,
                        userAnswerText = "",
                        aiFeedBack = AnswerAiFeedbackEntity(
                            ratingFromAi = 0,
                            goodPartAnswer = listOf(),
                            badPartAnswer = listOf()
                        )
                    )
                }
            }

            Intent.BackToFavouriteScreen -> _event.emit(Event.BackToFavouriteScreen)
        }
    }

    private suspend fun addQuestionToFavourite(isSavedByUser: Boolean) {
        addQuestionToFavouriteUseCase(
            QuestionEntity(
                questionTopic = state.value.topicOfQuestion,
                questionContent = state.value.questionText,
                categoryName = state.value.directionInIt,
                gradeName = state.value.grade,
            ),
            isSavedByUser = isSavedByUser
        )
    }

    private suspend fun loadAiFeedback() {

        _state.update {
            it.copy(
                isAiFeedbackLoading = true,
                shouldBeAiFeedbackBeShown = true,
                shouldBeCorrectAnswerBeShown = true,
            )
        }

        postUserAnswerUseCase(
            questionEntity = QuestionEntity(
                questionTopic = state.value.topicOfQuestion,
                questionContent = state.value.questionText,
                categoryName = state.value.directionInIt,
                gradeName = state.value.grade
            ),
            userAnswer = state.value.userAnswerText,
            correctAnswerEntity = CorrectAnswerEntity(state.value.correctAnswerText)
        )
            .onCompletion {
                _state.update { state ->
                    state.copy(
                        isAiFeedbackLoading = false
                    )
                }
                if (it == null) {
                    _state.update { state ->
                        state.copy(
                            numberOfLearnedQuestions = state.numberOfLearnedQuestions + 1,
                            numberOfVoiceAnsweredQuestions = state.numberOfVoiceAnsweredQuestions + 1,
                            pointsSum = state.pointsSum + state.aiFeedBack.ratingFromAi,
                        )
                    }
                } else {
                    _state.update { state ->
                        state.copy(
                            isAiFeedbackLoading = false,
                            isAiFeedbackVisible = false,
                            shouldBeAiFeedbackBeShown = false,
                            shouldBeCorrectAnswerBeShown = false,
                        )
                    }

                }
            }
            .collect { aiAnswer ->
                when (aiAnswer) {
                    is TResult.Error -> {
                        _state.update {
                            it.copy(
                                errorCause = aiAnswer.exception,
                                errorType = State.ErrorType.LoadingAiFeedbackError,
                            )
                        }
                    }

                    is TResult.Success -> {
                        _state.update {
                            it.copy(
                                aiFeedBack = aiAnswer.data,
                                isAiFeedbackVisible = true,
                            )
                        }
                    }
                }
            }
    }

    private suspend fun loadAnswer() {

        _state.update {
            it.copy(
                isAnswerVisible = false,
                isCorrectAnswerLoading = true,
            )
        }

        val questionEntity = QuestionEntity(
            questionTopic = state.value.topicOfQuestion,
            questionContent = state.value.questionText,
            categoryName = state.value.directionInIt,
            gradeName = state.value.grade
        )

        loadCorrectAnswerUseCase(
            questionEntity = questionEntity
        )
            .onCompletion {
                _state.update {
                    it.copy(
                        isCorrectAnswerLoading = false,
                    )
                }
                if (it == null) {
                    addQuestionToFavourite(false)
                }
            }
            .collect { answer ->
                when (answer) {
                    is TResult.Error -> {

                        stopTimerLogic()
                        stopRecordingUseCase()

                        if (state.value.shouldBeCorrectAnswerBeShown && !state.value.shouldBeAiFeedbackBeShown) {
                            _state.update { it.copy(numberOfLearnedQuestions = it.numberOfLearnedQuestions - 1) }
                        }

                        _state.update {
                            it.copy(
                                errorCause = answer.exception,
                                errorType = ErrorType.LoadingCorrectAnswerError,
                                isAiFeedbackLoading = false,
                                isAiFeedbackVisible = false,
                                shouldBeAiFeedbackBeShown = false,
                                shouldBeCorrectAnswerBeShown = false
                            )
                        }
                    }

                    is TResult.Success -> {
                        _state.update {
                            it.copy(
                                isAnswerVisible = true,
                                correctAnswerText = answer.data.answerContent,
                            )
                        }
                    }
                }
            }
    }

    private suspend fun loadQuestion() {

        _state.update {
            it.copy(
                isQuestionVisible = false,
                currentNumberOfQuestion = it.currentNumberOfQuestion + 1,
                isQuestionLoading = true
            )
        }
        loadQuestionUseCase(
            categoryName = state.value.directionInIt,
            gradeName = state.value.grade
        )
            .onCompletion {
                _state.update {
                    it.copy(
                        isQuestionLoading = false
                    )
                }
            }
            .collect { questionEntity ->
                when (questionEntity) {
                    is TResult.Error -> {
                        _state.update {
                            it.copy(
                                currentNumberOfQuestion = it.currentNumberOfQuestion - 1,
                                errorCause = questionEntity.exception,
                                errorType = State.ErrorType.LoadingQuestionError
                            )
                        }
                    }

                    is TResult.Success -> {
                        myLog(questionEntity.data.questionContent)
                        _state.update {
                            it.copy(
                                isQuestionVisible = true,
                                topicOfQuestion = questionEntity.data.questionTopic,
                                questionText = questionEntity.data.questionContent,
                            )
                        }

                    }
                }
            }
    }

    init {
        if (!state.value.answeringFromFavouriteMode) {
            loadingQuestionWithAnswer()
        } else {
            _state.update {
                it.copy(
                    isQuestionVisible = true,
                )
            }
            answeredJob = viewModelScope.launch {
                loadAnswer()
            }
        }
    }

    private fun loadingQuestionWithAnswer() {

        val job = viewModelScope.launch {
            loadQuestion()
        }
        answeredJob = viewModelScope.launch {
            job.join()

            if (state.value.errorCause == null) loadAnswer()

        }
    }

    init {
        viewModelScope.launch {
            getFavouriteQuestionsUseCase().collect { listOfFavouriteQuestions ->
                val questionsNames =
                    listOfFavouriteQuestions.map { it.questionContent }
                if (questionsNames.contains(state.value.questionText) && state.value.questionText.isNotEmpty()) {
                    _state.update { it.copy(isQuestionFavourite = true) }
                } else _state.update { it.copy(isQuestionFavourite = false) }

                _state.update { it.copy(favouriteAnswersAndQuestions = listOfFavouriteQuestions) }
            }
        }
    }

    private fun startTimerLogic() {
        timerJob.cancel()
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            val startTime = System.currentTimeMillis()
            val maxDuration = 120_000L

            while (isActive) {
                val elapsed = System.currentTimeMillis() - startTime

                if (elapsed >= maxDuration) {
                    _state.update { it.copy(recordingTime = maxDuration) }
                    sendIntent(Intent.SendUserAnswer)
                    break
                }

                _state.update { it.copy(recordingTime = elapsed) }
                delay(40L)
            }
        }
    }

    private fun stopTimerLogic() {
        timerJob.cancel()
    }


    override fun onCleared() {
        stopTimerLogic()
        stopRecordingUseCase()
        super.onCleared()
    }
}
