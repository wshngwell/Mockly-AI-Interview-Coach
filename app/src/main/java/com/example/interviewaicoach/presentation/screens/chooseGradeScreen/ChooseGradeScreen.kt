package com.example.interviewaicoach.presentation.screens.chooseGradeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.screens.chooseDirectionScreen.OptionsForInterViewBox
import com.example.interviewaicoach.presentation.screens.destinations.ChooseGradeScreenDestination
import com.example.interviewaicoach.presentation.screens.questionsWithAnswersScreen.getQuestionWithAnswersScreenDestination
import com.example.interviewaicoach.presentation.theme.beginInterviewFontSize
import com.example.interviewaicoach.presentation.theme.chooseDirectionFontSize
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.myBackGround
import com.example.interviewaicoach.presentation.theme.primaryTextColor
import com.example.interviewaicoach.presentation.theme.screenBottomAdditionalPadding
import com.example.interviewaicoach.presentation.theme.screenHorizontalPadding
import com.example.interviewaicoach.presentation.theme.screenTopPadding
import com.example.interviewaicoach.presentation.viemodels.GradeScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel


fun getChooseGradeScreenDestination(
    directionInIt: String,
) = ChooseGradeScreenDestination(directionInIt)

@RootNavGraph
@Destination
@Composable
fun ChooseGradeScreen(
    directionInIt: String,
    navigator: DestinationsNavigator
) {
    val viewModel = koinViewModel<GradeScreenViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val intent: (GradeScreenViewModel.Intent) -> Unit by remember(Unit) {
        mutableStateOf(viewModel::sendIntent)
    }

    val event: Flow<GradeScreenViewModel.Event> by remember {
        mutableStateOf(viewModel.event)
    }

    LaunchedEffect(Unit) {
        event.filterIsInstance<GradeScreenViewModel.Event>().collect {
            when (it) {
                is GradeScreenViewModel.Event.OnNavigateToQuestionScreen -> {
                    navigator.navigate(
                        getQuestionWithAnswersScreenDestination(
                            directionInIt,
                            it.gradeName
                        )
                    ) {
                        launchSingleTop = true
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
    state: GradeScreenViewModel.State = GradeScreenViewModel.State(),
    intent: (GradeScreenViewModel.Intent) -> Unit = {}
) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.begin_interview),
                fontFamily = mainAppFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = beginInterviewFontSize,
                color = primaryTextColor


            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.choose_your_grade),
                fontFamily = mainAppFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = chooseDirectionFontSize,
                color = primaryTextColor

            )
            Spacer(modifier = Modifier.height(20.dp))
            val gradesStringIds = remember {
                GradeUIModel.entries.map { it.gradeNameId }
            }
            OptionsForInterViewBox(
                listOfInterviewParamsStringsIds = gradesStringIds,
                interviewParamsButtonText = stringResource(R.string.begin_interview_text),
                onClickOnInterviewParamsButton = {
                    intent(GradeScreenViewModel.Intent.OnConfirmGrade)
                },
                onParamClicked = {
                    intent(GradeScreenViewModel.Intent.OnChangeCurrentGrade(it))
                },
                currentSelectedOption = state.gradeName
            )
        }

    }

}