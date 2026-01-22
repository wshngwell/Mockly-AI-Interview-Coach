package com.example.interviewaicoach.presentation.paramsForInterviewScreens

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
import com.example.interviewaicoach.presentation.ChooseDirectionNavBar
import com.example.interviewaicoach.presentation.DirectionUiModel
import com.example.interviewaicoach.presentation.OptionsForInterViewBox
import com.example.interviewaicoach.presentation.destinations.FavouriteQuestionsCategoriesScreenDestination
import com.example.interviewaicoach.presentation.destinations.SettingsScreenDestination
import com.example.interviewaicoach.presentation.theme.chooseDirectionFontSize
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.myBackGround
import com.example.interviewaicoach.presentation.theme.primaryTextColor
import com.example.interviewaicoach.presentation.theme.screenBottomAdditionalPadding
import com.example.interviewaicoach.presentation.theme.screenHorizontalPadding
import com.example.interviewaicoach.presentation.theme.screenTopPadding
import com.example.interviewaicoach.presentation.viemodels.DirectionScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel


@RootNavGraph(start = true)
@Destination
@Composable
fun ChooseDirectionScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<DirectionScreenViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val intent: (DirectionScreenViewModel.Intent) -> Unit by remember(Unit) {
        mutableStateOf(viewModel::sendIntent)
    }

    val event: Flow<DirectionScreenViewModel.Event> by remember {
        mutableStateOf(viewModel.event)
    }

    LaunchedEffect(Unit) {
        event.filterIsInstance<DirectionScreenViewModel.Event>().collect {
            when (it) {
                is DirectionScreenViewModel.Event.OnNavigateToGradeScreen -> {
                    navigator.navigate(getChooseGradeScreenDestination(it.directionInIt)) {
                        launchSingleTop = true
                    }
                }

                DirectionScreenViewModel.Event.OnNavigateToFavouriteQuestionScreen -> {
                    navigator.navigate(FavouriteQuestionsCategoriesScreenDestination) {
                        launchSingleTop = true
                    }
                }

                DirectionScreenViewModel.Event.OnSettingsIconClicked -> {
                    navigator.navigate(SettingsScreenDestination) {
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
    state: DirectionScreenViewModel.State = DirectionScreenViewModel.State(),
    intent: (DirectionScreenViewModel.Intent) -> Unit = {}
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
        ChooseDirectionNavBar(
            onFavouriteIconClicked = { intent(DirectionScreenViewModel.Intent.OnFavouriteIconClicked) },
            onSettingsIconClicked = { intent(DirectionScreenViewModel.Intent.OnSettingsIconClicked) }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.choose_the_direction),
                fontFamily = mainAppFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = chooseDirectionFontSize,
                color = primaryTextColor

            )
            Spacer(modifier = Modifier.height(20.dp))
            val directionsStringIds = remember {
                DirectionUiModel.entries.map { it.directionNameId }
            }
            OptionsForInterViewBox(
                listOfInterviewParamsStringsIds = directionsStringIds,
                interviewParamsButtonText = stringResource(R.string.next_button_text),
                onClickOnInterviewParamsButton = {
                    intent(DirectionScreenViewModel.Intent.OnConfirmDirectionInIt)
                },
                onParamClicked = {
                    intent(DirectionScreenViewModel.Intent.OnChangeCurrentDirectionInIt(it))
                },
                currentSelectedOption = state.directionInIt
            )
        }

    }
}