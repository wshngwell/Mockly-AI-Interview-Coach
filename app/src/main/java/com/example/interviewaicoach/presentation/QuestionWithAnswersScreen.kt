package com.example.interviewaicoach.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Loop
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interviewaicoach.R
import com.example.interviewaicoach.di.paramsForQuestionWithAnswerViewModel
import com.example.interviewaicoach.presentation.destinations.ChooseDirectionScreenDestination
import com.example.interviewaicoach.presentation.destinations.QuestionWithAnswersScreenDestination
import com.example.interviewaicoach.presentation.theme.borderOfRecordingStateView
import com.example.interviewaicoach.presentation.theme.clipParamsForBottomRecordingBoxOnQuestionsScreen
import com.example.interviewaicoach.presentation.theme.gradientBrushForMainButton
import com.example.interviewaicoach.presentation.theme.myBackGround
import com.example.interviewaicoach.presentation.theme.questionsWithAnswersBottomButtonsGradient
import com.example.interviewaicoach.presentation.theme.redIconColor
import com.example.interviewaicoach.presentation.theme.screenBottomAdditionalPadding
import com.example.interviewaicoach.presentation.theme.screenHorizontalPadding
import com.example.interviewaicoach.presentation.theme.screenTopPadding
import com.example.interviewaicoach.presentation.theme.sizeOfRecordingIcon
import com.example.interviewaicoach.presentation.theme.transparentGradientBrush
import com.example.interviewaicoach.presentation.viemodels.QuestionsWithAnswersViewModel
import com.example.interviewaicoach.presentation.viemodels.QuestionsWithAnswersViewModel.Event
import com.example.interviewaicoach.presentation.viemodels.QuestionsWithAnswersViewModel.Intent
import com.example.interviewaicoach.presentation.viemodels.QuestionsWithAnswersViewModel.State
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel


fun getQuestionWithAnswersScreenDestination(
    directionInIt: String,
    gradeName: String,
) = QuestionWithAnswersScreenDestination(directionInIt, gradeName)

@RootNavGraph
@Destination
@Composable
fun QuestionWithAnswersScreen(
    directionInIt: String,
    gradeName: String,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<QuestionsWithAnswersViewModel>(
        parameters = { paramsForQuestionWithAnswerViewModel(directionInIt, gradeName) }
    )

    val state by viewModel.state.collectAsStateWithLifecycle()
    val intent: (Intent) -> Unit by remember {
        mutableStateOf(viewModel::sendIntent)
    }
    val event: Flow<Event> by remember {
        mutableStateOf(viewModel.event)
    }

    LaunchedEffect(Unit) {

        event.filterIsInstance<Event>().collect {
            when (it) {

                is Event.NavigateToResultsScreen -> {
                    navigator.navigate(
                        getResultScreenDestination(
                            it.directionInIt,
                            it.gradeName,
                            it.pointsSum,
                            it.countsOfQuestions,
                            it.numberOfSavedQuestions,
                            it.numberOfVoiceAnsweredQuestions
                        )
                    ) {
                        launchSingleTop = true
                        popUpTo(ChooseDirectionScreenDestination)
                    }
                }
            }
        }
    }

    UI(
        state = state,
        intent = intent
    )

}

@Preview
@Composable
private fun UI(
    state: State = State(),
    intent: (Intent) -> Unit = {},
) {
    val requestRecordAudio = PermissionLauncher.build(
        permissionName = PermissionLauncher.RECORD_AUDIO,
        onSuccess = {
            intent(Intent.StartRecording)
        },
        onFailure = {}
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(myBackGround)
            .padding(
                top = screenTopPadding,
                start = screenHorizontalPadding,
                end = screenHorizontalPadding,
                bottom = screenBottomAdditionalPadding
            )
    ) {

        QuestionWithAnswersScreenControlPanel(
            text = "${state.directionInIt} · ${state.grade}",
            onCrossClicked = { intent(Intent.EndInterview) },
        )
        QuestionWithAnswerCard(
            error = state.errorCause,
            isQuestionVisible = state.isQuestionVisible,
            isAiFeedBackVisible = state.isAiFeedbackVisible,
            isCorrectAnswerVisible = state.isAnswerVisible,
            topicOfQuestionText = state.topicOfQuestion,
            questionText = state.questionText,
            answerFromAi = state.aiFeedBack,
            correctAnswer = state.correctAnswerText,
            shouldBeAiFeedbackBeShown = state.shouldBeAiFeedbackBeShown,
            shouldBeCorrectAnswerBeShown = state.shouldBeCorrectAnswerBeShown,
            onRetryAfterError = { intent(Intent.Retry) }
        )
        if (
            state.shouldBeAiFeedbackBeShown || state.shouldBeCorrectAnswerBeShown
        ) {
            BottomElementsBar(
                onRightButtonClicked = { intent(Intent.LoadNextQuestion) },
                onLeftButtonClicked = { intent(Intent.AddOrDeleteToFavourite) },
                shouldBeButtonsDisabled = state.errorCause != null
                        || state.isCorrectAnswerLoading
                        || state.isAiFeedbackLoading,
                isRecording = state.isRecording,
                rightIcon = Icons.Outlined.ArrowForward,
                leftIcon = if (state.isQuestionFavourite) Icons.Outlined.Delete else Icons.Outlined.BookmarkBorder,
                rightElementText = stringResource(R.string.next_question),
                leftElementText = if (state.isQuestionFavourite)
                    stringResource(
                        R.string.delete_question
                    ) else stringResource(
                    R.string.save_question
                ),
                leftButtonBrush = questionsWithAnswersBottomButtonsGradient,
                rightButtonBrush = questionsWithAnswersBottomButtonsGradient,
            )
        }
        if (state.isRecording && state.errorCause == null) {
            BottomElementsBar(
                onRightButtonClicked = { intent(Intent.SendUserAnswer) },
                shouldBeButtonsDisabled = state.errorCause != null,
                isRecording = state.isRecording,
                rightIcon = Icons.Outlined.ArrowUpward,
                leftIcon = Icons.Filled.FiberManualRecord,
                rightElementText = stringResource(R.string.next_question),
                leftElementText = formatRecordingTime(state.recordingTime),
                leftIconColor = redIconColor,
                sizeOfLeftIcon = sizeOfRecordingIcon,
                widthOfBorderDp = borderOfRecordingStateView,
                brushForBorder = questionsWithAnswersBottomButtonsGradient,
                shapeForBorder = RoundedCornerShape(
                    clipParamsForBottomRecordingBoxOnQuestionsScreen
                ),
                rightButtonBrush = gradientBrushForMainButton,
                leftButtonBrush = transparentGradientBrush,
            )
        }
        if (!state.isRecording && !state.shouldBeAiFeedbackBeShown && !state.shouldBeCorrectAnswerBeShown) {
            BottomElementsBar(
                onRightButtonClicked = { requestRecordAudio.launch() },
                onLeftButtonClicked = { intent(Intent.ShowCorrectAnswer) },
                shouldBeButtonsDisabled = state.errorCause != null || state.isQuestionLoading,
                rightIcon = Icons.Outlined.Mic,
                leftIcon = Icons.Outlined.Loop,
                rightElementText = stringResource(R.string.record_voice),
                leftElementText = stringResource(R.string.see_answer),
                leftButtonBrush = questionsWithAnswersBottomButtonsGradient,
                rightButtonBrush = gradientBrushForMainButton,
                isIconSecondButtonLeft = true,
            )
        }
    }
}