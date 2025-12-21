package com.example.interviewaicoach.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interviewaicoach.R
import com.example.interviewaicoach.di.paramsForResultScreeViewModel
import com.example.interviewaicoach.presentation.destinations.ChooseDirectionScreenDestination
import com.example.interviewaicoach.presentation.destinations.ChooseGradeScreenDestination
import com.example.interviewaicoach.presentation.destinations.ResultsScreenDestination
import com.example.interviewaicoach.presentation.theme.darkThemeTextColor
import com.example.interviewaicoach.presentation.theme.finalResultCommonContentHorizontalPadding
import com.example.interviewaicoach.presentation.theme.finalResultIconSize
import com.example.interviewaicoach.presentation.theme.gradientBrushForMainButton
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.myBackGround
import com.example.interviewaicoach.presentation.theme.numberAndTopicOfQuestionColor
import com.example.interviewaicoach.presentation.theme.numberAndTopicOfQuestionFontSize
import com.example.interviewaicoach.presentation.theme.questionFontSize
import com.example.interviewaicoach.presentation.theme.questionsWithAnswersBottomButtonsGradient
import com.example.interviewaicoach.presentation.theme.resultsCommonPaddingVertical
import com.example.interviewaicoach.presentation.theme.screenBottomAdditionalPadding
import com.example.interviewaicoach.presentation.theme.screenHorizontalPadding
import com.example.interviewaicoach.presentation.theme.screenResultsTopPadding
import com.example.interviewaicoach.presentation.viemodels.ResultScreenViewModel
import com.example.interviewaicoach.presentation.viemodels.ResultScreenViewModel.State
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel


fun getResultScreenDestination(
    directionName: String,
    gradeName: String,
    pointsSum: Int,
    countsOfQuestions: Int,
    numberOfSavedQuestions: Int,
    numberOfVoiceAnsweredQuestions: Int
) = ResultsScreenDestination(
    directionName,
    gradeName,
    pointsSum,
    countsOfQuestions,
    numberOfSavedQuestions,
    numberOfVoiceAnsweredQuestions
)

@RootNavGraph
@Destination
@Composable
fun ResultsScreen(
    directionName: String,
    gradeName: String,
    pointsSum: Int,
    countsOfQuestions: Int,
    numberOfSavedQuestions: Int,
    numberOfVoiceAnsweredQuestions: Int,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<ResultScreenViewModel>(
        parameters = {
            paramsForResultScreeViewModel(
                pointsSum,
                countsOfQuestions,
                numberOfSavedQuestions,
                numberOfVoiceAnsweredQuestions
            )
        }
    )

    val state by viewModel.state.collectAsStateWithLifecycle()
    val intent: (ResultScreenViewModel.Intent) -> Unit by remember(Unit) {
        mutableStateOf(viewModel::sendIntent)
    }

    val event: Flow<ResultScreenViewModel.Event> by remember {
        mutableStateOf(viewModel.event)
    }

    LaunchedEffect(Unit) {
        event.filterIsInstance<ResultScreenViewModel.Event>().collect {
            when (it) {
                ResultScreenViewModel.Event.NavigateOnMainScreen -> {

                    navigator.popBackStack(
                        route = ChooseDirectionScreenDestination,
                        inclusive = false
                    )
                }

                is ResultScreenViewModel.Event.NavigateOnQuestionsScreenAndRetry -> {

                    navigator.navigate(
                        getQuestionWithAnswersScreenDestination(
                            directionName, gradeName
                        )
                    ) {
                        popUpTo(ChooseGradeScreenDestination)
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
    state: State = State(
        8,
        2, 1, 2
    ),
    intent: (ResultScreenViewModel.Intent) -> Unit = {}

) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(myBackGround)
            .padding(
                top = screenResultsTopPadding,
                start = screenHorizontalPadding,
                end = screenHorizontalPadding,
                bottom = screenBottomAdditionalPadding
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier
                    .size(finalResultIconSize),
                painter = painterResource(R.drawable.final_result_icon),
                contentDescription = stringResource(R.string.results_icon)
            )

            Text(
                modifier = Modifier.padding(horizontal = finalResultCommonContentHorizontalPadding),
                text = stringResource(R.string.interview_completed),
                fontFamily = mainAppFontFamily,
                fontWeight = FontWeight.Bold,
                color = darkThemeTextColor,
                fontSize = questionFontSize,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.padding(
                    top = resultsCommonPaddingVertical,
                    start = finalResultCommonContentHorizontalPadding,
                    end = finalResultCommonContentHorizontalPadding
                ),
                text = stringResource(R.string.final_text),
                fontFamily = mainAppFontFamily,
                fontWeight = FontWeight.Bold,
                color = numberAndTopicOfQuestionColor,
                fontSize = numberAndTopicOfQuestionFontSize,
                textAlign = TextAlign.Center
            )

            FinalResultsCard(
                averageScore = if (state.countsOfQuestions == 0) 0 else state.pointsSum / state.countsOfQuestions,
                numberOfSavedQuestions = state.numberOfSavedQuestions,
                numberOfVoiceAnsweredQuestions = state.numberOfVoiceAnsweredQuestions,
                numberOfQuestions = state.countsOfQuestions
            )
        }

        BottomElementsBar(
            onRightButtonClicked = { intent(ResultScreenViewModel.Intent.OnRetryButtonClicked) },
            onLeftButtonClicked = { intent(ResultScreenViewModel.Intent.OnGoToMainScreenButtonClicked) },
            rightIcon = Icons.Outlined.Sync,
            leftIcon = Icons.AutoMirrored.Outlined.Logout,
            rightElementText = stringResource(R.string.retry),
            leftElementText = stringResource(R.string.onMainScreen),
            leftButtonBrush = questionsWithAnswersBottomButtonsGradient,
            rightButtonBrush = gradientBrushForMainButton,
            isIconSecondButtonLeft = true
        )


    }
}