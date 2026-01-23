package com.example.interviewaicoach.presentation.screens.favouriteCategoriesScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.screens.questionsWithAnswersScreen.QuestionsNavBar
import com.example.interviewaicoach.presentation.screens.favouriteQuestionsScreen.getFavouriteQuestionsInCategoryScreenDestination
import com.example.interviewaicoach.presentation.screens.chooseDirectionScreen.DirectionUiModel
import com.example.interviewaicoach.presentation.screens.destinations.ChooseDirectionScreenDestination
import com.example.interviewaicoach.presentation.screens.favouriteQuestionsScreen.FavouriteQuestionCard
import com.example.interviewaicoach.presentation.theme.cardColor
import com.example.interviewaicoach.presentation.theme.clipParamsForQuestionWithAnswerBox
import com.example.interviewaicoach.presentation.theme.favouriteQuestionCardCategoryHorizontalPadding
import com.example.interviewaicoach.presentation.theme.horizontalDividerVerticalPadding
import com.example.interviewaicoach.presentation.theme.myBackGround
import com.example.interviewaicoach.presentation.theme.questionWIthAnswerCardInnerPadding
import com.example.interviewaicoach.presentation.theme.questionWIthAnswerCardTopOuterPadding
import com.example.interviewaicoach.presentation.theme.screenBottomAdditionalPadding
import com.example.interviewaicoach.presentation.theme.screenHorizontalPadding
import com.example.interviewaicoach.presentation.theme.screenTopPadding
import com.example.interviewaicoach.presentation.viemodels.FavouriteCategoriesViewModel
import com.example.interviewaicoach.presentation.viemodels.FavouriteCategoriesViewModel.Event
import com.example.interviewaicoach.presentation.viemodels.FavouriteCategoriesViewModel.Intent
import com.example.interviewaicoach.presentation.viemodels.FavouriteCategoriesViewModel.State
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel

@RootNavGraph
@Destination
@Composable
fun FavouriteQuestionsCategoriesScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<FavouriteCategoriesViewModel>()

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
                is Event.OnNavigateToChooseDirectionsScreen -> {
                    navigator.navigate(ChooseDirectionScreenDestination) {
                        launchSingleTop = true
                        popUpTo(ChooseDirectionScreenDestination)
                    }
                }

                is Event.OnNavigateToFavouriteQuestionsInCategoryScreen -> {
                    navigator.navigate(
                        getFavouriteQuestionsInCategoryScreenDestination(
                            categoryName = it.category
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
    state: State = State(),
    intent: (Intent) -> Unit = {}
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
        QuestionsNavBar(
            text = stringResource(R.string.my_questions),
            onLeftIconClicked = { intent(Intent.OnCloseScreen) },
        )

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(top = questionWIthAnswerCardTopOuterPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            val context = LocalContext.current

            DirectionUiModel.entries.forEach {
                FavouriteQuestionCard(
                    modifier = Modifier
                        .padding(
                            bottom = horizontalDividerVerticalPadding
                        )
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(clipParamsForQuestionWithAnswerBox))
                        .background(color = cardColor)
                        .clickable { intent(Intent.OnCategoryClicked(context.getString(it.directionNameId))) }
                        .padding(
                            horizontal = questionWIthAnswerCardInnerPadding,
                            vertical = favouriteQuestionCardCategoryHorizontalPadding
                        ),
                    text = context.getString(it.directionNameId)
                )
            }

        }
    }
}