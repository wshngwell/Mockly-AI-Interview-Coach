package com.example.interviewaicoach.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interviewaicoach.di.paramsForFavouriteQuestionsInCategoryScreenViewModel
import com.example.interviewaicoach.presentation.destinations.FavouriteQuestionsCategoriesScreenDestination
import com.example.interviewaicoach.presentation.destinations.FavouriteQuestionsInCategoryScreenDestination
import com.example.interviewaicoach.presentation.theme.clipParamsForQuestionWithAnswerBox
import com.example.interviewaicoach.presentation.theme.favCategoryArrowColor
import com.example.interviewaicoach.presentation.theme.favouriteQuestionCardCategoryHorizontalPadding
import com.example.interviewaicoach.presentation.theme.horizontalDividerVerticalPadding
import com.example.interviewaicoach.presentation.theme.horizontalFavQuestionsCardPadding
import com.example.interviewaicoach.presentation.theme.myBackGround
import com.example.interviewaicoach.presentation.theme.questionWIthAnswerCardInnerPadding
import com.example.interviewaicoach.presentation.theme.questionWIthAnswerCardTopOuterPadding
import com.example.interviewaicoach.presentation.theme.questionWithAnswerCardColor
import com.example.interviewaicoach.presentation.theme.screenBottomAdditionalPadding
import com.example.interviewaicoach.presentation.theme.screenHorizontalPadding
import com.example.interviewaicoach.presentation.theme.screenTopPadding
import com.example.interviewaicoach.presentation.theme.verticalFavQuestionCardPadding
import com.example.interviewaicoach.presentation.viemodels.FavouriteQuestionsInCategoryScreenViewModel
import com.example.interviewaicoach.presentation.viemodels.FavouriteQuestionsInCategoryScreenViewModel.Event
import com.example.interviewaicoach.presentation.viemodels.FavouriteQuestionsInCategoryScreenViewModel.Event.OnNavigateToQuestionWithAnswerScreen
import com.example.interviewaicoach.presentation.viemodels.FavouriteQuestionsInCategoryScreenViewModel.Intent.OnGoBackIconClicked
import com.example.interviewaicoach.presentation.viemodels.FavouriteQuestionsInCategoryScreenViewModel.State
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel


fun getFavouriteQuestionsInCategoryScreenDestination(categoryName: String) =
    FavouriteQuestionsInCategoryScreenDestination(
        categoryName = categoryName
    )

@RootNavGraph
@Destination
@Composable
fun FavouriteQuestionsInCategoryScreen(
    navigator: DestinationsNavigator,
    categoryName: String
) {
    val viewModel = koinViewModel<FavouriteQuestionsInCategoryScreenViewModel>(
        parameters = { paramsForFavouriteQuestionsInCategoryScreenViewModel(categoryName) }
    )

    val state by viewModel.state.collectAsStateWithLifecycle()
    val intent: (FavouriteQuestionsInCategoryScreenViewModel.Intent) -> Unit by remember {
        mutableStateOf(viewModel::sendIntent)
    }
    val event: Flow<Event> by remember {
        mutableStateOf(viewModel.event)
    }

    LaunchedEffect(Unit) {

        event.filterIsInstance<Event>().collect {
            when (it) {

                is Event.OnNavigateToChooseFavQuestionsCategoryScreen -> {
                    navigator.navigate(FavouriteQuestionsCategoriesScreenDestination) {
                        launchSingleTop = true
                        popUpTo(FavouriteQuestionsCategoriesScreenDestination)
                    }
                }

                is OnNavigateToQuestionWithAnswerScreen -> {}
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
        ""
    ),
    intent: (FavouriteQuestionsInCategoryScreenViewModel.Intent) -> Unit = {}
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
            text = state.categoryName,
            onLeftIconClicked = { intent(OnGoBackIconClicked) },
            shouldBeRightIconButton = true,
            leftIcon = Icons.AutoMirrored.Outlined.ArrowBack
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = questionWIthAnswerCardTopOuterPadding)
                .fillMaxSize()

        ) {

            if (state.juniorQuestionsList.questionsList.isNotEmpty()) {
                item {
                    FavouriteQuestionCard(
                        modifier = Modifier
                            .padding(
                                bottom = horizontalDividerVerticalPadding
                            )
                            .fillMaxWidth()
                            .padding(
                                horizontal = horizontalFavQuestionsCardPadding,
                                vertical = verticalFavQuestionCardPadding
                            ),
                        text = stringResource(GradeUIModel.JUNIOR.gradeNameId),
                        shouldBeArrowInTheEnd = true,
                        isExpanded = state.juniorQuestionsList.isExpanded,
                        shouldBeArrowClicked = true,
                        onArrowClick = {
                            intent(
                                FavouriteQuestionsInCategoryScreenViewModel.Intent.OnExpandGradeStateChange(
                                    GradeUIModel.JUNIOR
                                )
                            )
                        },
                        textFontColor = favCategoryArrowColor
                    )
                }

                if (state.juniorQuestionsList.isExpanded) {
                    items(
                        items = state.juniorQuestionsList.questionsList,
                        key = { it.toString() }
                    ) {
                        FavouriteQuestionCard(
                            modifier = Modifier
                                .padding(
                                    bottom = horizontalDividerVerticalPadding
                                )
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(clipParamsForQuestionWithAnswerBox))
                                .background(color = questionWithAnswerCardColor)
                                .clickable {
                                    intent(
                                        FavouriteQuestionsInCategoryScreenViewModel.Intent.OnQuestionClicked(
                                            it
                                        )
                                    )
                                }
                                .padding(
                                    horizontal = questionWIthAnswerCardInnerPadding,
                                    vertical = favouriteQuestionCardCategoryHorizontalPadding
                                ),
                            text = it.questionContent,
                            shouldBeArrowInTheEnd = false
                        )
                    }
                }
            }

            if (state.middleQuestionsList.questionsList.isNotEmpty()) {
                item {
                    FavouriteQuestionCard(
                        modifier = Modifier
                            .padding(
                                bottom = horizontalDividerVerticalPadding
                            )
                            .fillMaxWidth()
                            .padding(
                                horizontal = horizontalFavQuestionsCardPadding,
                                vertical = verticalFavQuestionCardPadding
                            ),
                        text = stringResource(GradeUIModel.MIDDLE.gradeNameId),
                        shouldBeArrowInTheEnd = true,
                        isExpanded = state.middleQuestionsList.isExpanded,
                        shouldBeArrowClicked = true,
                        onArrowClick = {
                            intent(
                                FavouriteQuestionsInCategoryScreenViewModel.Intent.OnExpandGradeStateChange(
                                    GradeUIModel.MIDDLE
                                )
                            )
                        },
                        textFontColor = favCategoryArrowColor
                    )
                }

                if (state.middleQuestionsList.isExpanded) {
                    items(
                        items = state.middleQuestionsList.questionsList,
                        key = { it.toString() }
                    ) {
                        FavouriteQuestionCard(
                            modifier = Modifier
                                .padding(
                                    bottom = horizontalDividerVerticalPadding
                                )
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(clipParamsForQuestionWithAnswerBox))
                                .background(color = questionWithAnswerCardColor)
                                .clickable {
                                    intent(
                                        FavouriteQuestionsInCategoryScreenViewModel.Intent.OnQuestionClicked(
                                            it
                                        )
                                    )
                                }
                                .padding(
                                    horizontal = questionWIthAnswerCardInnerPadding,
                                    vertical = favouriteQuestionCardCategoryHorizontalPadding
                                ),
                            text = it.questionContent,
                            shouldBeArrowInTheEnd = false
                        )
                    }
                }


            }
            if (state.seniorQuestionsList.questionsList.isNotEmpty()) {
                item {
                    FavouriteQuestionCard(
                        modifier = Modifier
                            .padding(
                                bottom = horizontalDividerVerticalPadding
                            )
                            .fillMaxWidth()
                            .padding(
                                horizontal = horizontalFavQuestionsCardPadding,
                                vertical = verticalFavQuestionCardPadding
                            ),
                        text = stringResource(GradeUIModel.SENIOR.gradeNameId),
                        shouldBeArrowInTheEnd = true,
                        isExpanded = state.seniorQuestionsList.isExpanded,
                        shouldBeArrowClicked = true,
                        onArrowClick = {
                            intent(
                                FavouriteQuestionsInCategoryScreenViewModel.Intent.OnExpandGradeStateChange(
                                    GradeUIModel.SENIOR
                                )
                            )
                        },
                        textFontColor = favCategoryArrowColor
                    )
                }

                if (state.seniorQuestionsList.isExpanded) {
                    items(
                        items = state.seniorQuestionsList.questionsList,
                        key = { it.toString() }
                    ) {
                        FavouriteQuestionCard(
                            modifier = Modifier
                                .padding(
                                    bottom = horizontalDividerVerticalPadding
                                )
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(clipParamsForQuestionWithAnswerBox))
                                .background(color = questionWithAnswerCardColor)
                                .clickable {
                                    intent(
                                        FavouriteQuestionsInCategoryScreenViewModel.Intent.OnQuestionClicked(
                                            it
                                        )
                                    )
                                }
                                .padding(
                                    horizontal = questionWIthAnswerCardInnerPadding,
                                    vertical = favouriteQuestionCardCategoryHorizontalPadding
                                ),
                            text = it.questionContent,
                            shouldBeArrowInTheEnd = false
                        )
                    }
                }


            }
            if (state.leadQuestionsList.questionsList.isNotEmpty()) {
                item {
                    FavouriteQuestionCard(
                        modifier = Modifier
                            .padding(
                                bottom = horizontalDividerVerticalPadding
                            )
                            .fillMaxWidth()
                            .padding(
                                horizontal = horizontalFavQuestionsCardPadding,
                                vertical = verticalFavQuestionCardPadding
                            ),
                        text = stringResource(GradeUIModel.LEAD.gradeNameId),
                        shouldBeArrowInTheEnd = true,
                        isExpanded = state.leadQuestionsList.isExpanded,
                        shouldBeArrowClicked = true,
                        onArrowClick = {
                            intent(
                                FavouriteQuestionsInCategoryScreenViewModel.Intent.OnExpandGradeStateChange(
                                    GradeUIModel.LEAD
                                )
                            )
                        },
                        textFontColor = favCategoryArrowColor
                    )
                }

                if (state.leadQuestionsList.isExpanded) {
                    items(
                        items = state.leadQuestionsList.questionsList,
                        key = { it.toString() }
                    ) {
                        FavouriteQuestionCard(
                            modifier = Modifier
                                .padding(
                                    bottom = horizontalDividerVerticalPadding
                                )
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(clipParamsForQuestionWithAnswerBox))
                                .background(color = questionWithAnswerCardColor)
                                .clickable {
                                    intent(
                                        FavouriteQuestionsInCategoryScreenViewModel.Intent.OnQuestionClicked(
                                            it
                                        )
                                    )
                                }
                                .padding(
                                    horizontal = questionWIthAnswerCardInnerPadding,
                                    vertical = favouriteQuestionCardCategoryHorizontalPadding
                                ),
                            text = it.questionContent,
                            shouldBeArrowInTheEnd = false
                        )
                    }
                }
            }
        }
    }
}

