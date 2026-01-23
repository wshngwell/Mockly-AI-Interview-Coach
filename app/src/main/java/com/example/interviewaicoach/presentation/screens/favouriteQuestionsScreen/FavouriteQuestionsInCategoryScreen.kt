package com.example.interviewaicoach.presentation.screens.favouriteQuestionsScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interviewaicoach.R
import com.example.interviewaicoach.di.paramsForFavouriteQuestionsInCategoryScreenViewModel
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.QuestionEntity
import com.example.interviewaicoach.presentation.screens.chooseGradeScreen.GradeUIModel
import com.example.interviewaicoach.presentation.screens.questionsWithAnswersScreen.QuestionsNavBar
import com.example.interviewaicoach.presentation.screens.questionsWithAnswersScreen.getQuestionWithAnswersScreenDestination
import com.example.interviewaicoach.presentation.screens.chooseGradeScreen.getChooseGradeScreenDestination
import com.example.interviewaicoach.presentation.screens.destinations.ChooseDirectionScreenDestination
import com.example.interviewaicoach.presentation.screens.destinations.FavouriteQuestionsCategoriesScreenDestination
import com.example.interviewaicoach.presentation.screens.destinations.FavouriteQuestionsInCategoryScreenDestination
import com.example.interviewaicoach.presentation.screens.questionsWithAnswersScreen.ErrorCard
import com.example.interviewaicoach.presentation.theme.cardColor
import com.example.interviewaicoach.presentation.theme.clipParamsForQuestionWithAnswerBox
import com.example.interviewaicoach.presentation.theme.favouriteQuestionCardCategoryHorizontalPadding
import com.example.interviewaicoach.presentation.theme.horizontalDividerVerticalPadding
import com.example.interviewaicoach.presentation.theme.horizontalFavQuestionsCardPadding
import com.example.interviewaicoach.presentation.theme.myBackGround
import com.example.interviewaicoach.presentation.theme.questionFavouriteCardHeight
import com.example.interviewaicoach.presentation.theme.questionWIthAnswerCardInnerPadding
import com.example.interviewaicoach.presentation.theme.questionWIthAnswerCardTopOuterPadding
import com.example.interviewaicoach.presentation.theme.screenBottomAdditionalPadding
import com.example.interviewaicoach.presentation.theme.screenHorizontalPadding
import com.example.interviewaicoach.presentation.theme.screenTopPadding
import com.example.interviewaicoach.presentation.theme.secondaryTextColor
import com.example.interviewaicoach.presentation.theme.verticalFavQuestionCardPadding
import com.example.interviewaicoach.presentation.viemodels.FavouriteQuestionsInCategoryScreenViewModel
import com.example.interviewaicoach.presentation.viemodels.FavouriteQuestionsInCategoryScreenViewModel.Event
import com.example.interviewaicoach.presentation.viemodels.FavouriteQuestionsInCategoryScreenViewModel.Event.OnNavigateToQuestionWithAnswerScreen
import com.example.interviewaicoach.presentation.viemodels.FavouriteQuestionsInCategoryScreenViewModel.Intent
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
    val intent: (Intent) -> Unit by remember {
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

                is OnNavigateToQuestionWithAnswerScreen -> {
                    navigator.navigate(
                        getQuestionWithAnswersScreenDestination(
                            it.questionEntity.categoryName,
                            gradeName = it.questionEntity.gradeName,
                            answeringFromFavouriteMode = true,
                            questionEntity = it.questionEntity
                        )
                    ) {
                        launchSingleTop = true
                    }
                }

                Event.BeginInterviewClicked -> {
                    navigator.navigate(getChooseGradeScreenDestination(state.categoryName)) {
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
    state: State = State(
        ""
    ),
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
        var expanded by rememberSaveable { mutableStateOf(false) }
        val errorScrollState = rememberScrollState()

        QuestionsNavBar(
            text = state.categoryName,
            onLeftIconClicked = { intent(OnGoBackIconClicked) },
            shouldBeRightIconButton = true,
            shouldBeRightIconClicked = !state.isEmptyFavouriteList,
            leftIcon = if (state.isDeletingMode) Icons.Outlined.Delete else Icons.AutoMirrored.Outlined.ArrowBack,
            rightIcon = if (state.isDeletingMode) Icons.Outlined.Done else Icons.Outlined.MoreVert,
            isExpandedDropDownMenu = expanded,
            onRightIconClicked = {
                if (state.isDeletingMode) {
                    intent(Intent.CancelDeleting)
                } else {
                    expanded = expanded.not()
                }
            },
            onDismissDropDownMenu = { expanded = false },
            onChooseItemInDropDownMenuClicked = {
                intent(Intent.OnDeletingModeStateChange)
                expanded = false
            },
            onDeleteAllItemInDropDownMenuClicked = {
                intent(Intent.DeleteAllItems)
                expanded = false
            },
            onDeleteSelectedItems = {
                intent(Intent.DeleteSelectedItems)
            },
            isDeletingMode =
                if (state.isEmptyFavouriteList) false
                else if (state.isDeletingMode) true
                else false,
            countOfSelectedQuestions = state.countOfSelectedQuestions
        )
        if (state.isEmptyFavouriteList) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(errorScrollState),
                verticalArrangement = Arrangement.Center,
                Alignment.CenterHorizontally

            ) {
                ErrorCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 36.dp,
                            horizontal = 30.dp
                        ),
                    onButtonClicked = {
                        intent(Intent.BeginInterviewClicked)
                    },
                    errorTextId = R.string.empty_fav_list_heading,
                    errorDescriptionId = R.string.empty_fav_list_desc,
                    imageResourceId = R.drawable.empty_saved_questions,
                    buttonText = stringResource(R.string.begin_interview),
                    isFavouriteQuestionsEmptyError = true
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(top = questionWIthAnswerCardTopOuterPadding)
                    .fillMaxSize()

            ) {
                cardForGrade(
                    questionsWithCheckBoxList = state.juniorQuestionsList.questionsWithCheckBoxList,
                    onExpandCardTap = {
                        intent(
                            Intent.OnExpandGradeStateChange(
                                GradeUIModel.JUNIOR
                            )
                        )
                    },
                    gradeTextRes = GradeUIModel.JUNIOR.gradeNameId,
                    onQuestionCardClicked = {
                        intent(
                            Intent.OnQuestionClicked(
                                it
                            )
                        )
                    },
                    onCheckBoxClicked = {
                        intent(
                            Intent.OnCheckedQuestionStateChange(
                                checked = it.isChecked,
                                questionEntity = it.questionEntity,
                                gradeUiModel = GradeUIModel.JUNIOR
                            )
                        )
                    },
                    isExpanded = state.juniorQuestionsList.isExpanded,
                    isDeletingMode = state.isDeletingMode
                )

                cardForGrade(
                    questionsWithCheckBoxList = state.middleQuestionsList.questionsWithCheckBoxList,
                    onExpandCardTap = {
                        intent(
                            Intent.OnExpandGradeStateChange(
                                GradeUIModel.MIDDLE
                            )
                        )
                    },
                    gradeTextRes = GradeUIModel.MIDDLE.gradeNameId,
                    onQuestionCardClicked = {
                        intent(
                            Intent.OnQuestionClicked(
                                it
                            )
                        )
                    },
                    onCheckBoxClicked = {
                        intent(
                            Intent.OnCheckedQuestionStateChange(
                                checked = it.isChecked,
                                questionEntity = it.questionEntity,
                                gradeUiModel = GradeUIModel.MIDDLE
                            )
                        )
                    },
                    isExpanded = state.middleQuestionsList.isExpanded,
                    isDeletingMode = state.isDeletingMode
                )

                cardForGrade(
                    questionsWithCheckBoxList = state.seniorQuestionsList.questionsWithCheckBoxList,
                    onExpandCardTap = {
                        intent(
                            Intent.OnExpandGradeStateChange(
                                GradeUIModel.SENIOR
                            )
                        )
                    },
                    onQuestionCardClicked = {
                        intent(
                            Intent.OnQuestionClicked(
                                it
                            )
                        )
                    },
                    gradeTextRes = GradeUIModel.SENIOR.gradeNameId,
                    onCheckBoxClicked = {
                        intent(
                            Intent.OnCheckedQuestionStateChange(
                                checked = it.isChecked,
                                questionEntity = it.questionEntity,
                                gradeUiModel = GradeUIModel.SENIOR
                            )
                        )
                    },
                    isExpanded = state.seniorQuestionsList.isExpanded,
                    isDeletingMode = state.isDeletingMode
                )
                cardForGrade(
                    questionsWithCheckBoxList = state.leadQuestionsList.questionsWithCheckBoxList,
                    onExpandCardTap = {
                        intent(
                            Intent.OnExpandGradeStateChange(
                                GradeUIModel.LEAD
                            )
                        )
                    },
                    onQuestionCardClicked = {
                        intent(
                            Intent.OnQuestionClicked(
                                it
                            )
                        )
                    },
                    onCheckBoxClicked = {
                        intent(
                            Intent.OnCheckedQuestionStateChange(
                                checked = it.isChecked,
                                questionEntity = it.questionEntity,
                                gradeUiModel = GradeUIModel.LEAD
                            )
                        )
                    },
                    gradeTextRes = GradeUIModel.LEAD.gradeNameId,
                    isExpanded = state.leadQuestionsList.isExpanded,
                    isDeletingMode = state.isDeletingMode
                )

            }
        }
    }
}

private fun LazyListScope.cardForGrade(
    questionsWithCheckBoxList: List<QuestionEntityWithCheckBoxUiModel>,
    gradeTextRes: Int = GradeUIModel.JUNIOR.gradeNameId,
    onExpandCardTap: () -> Unit = {},
    onQuestionCardClicked: (QuestionEntity) -> Unit = {},
    onCheckBoxClicked: (QuestionEntityWithCheckBoxUiModel) -> Unit = {},
    isExpanded: Boolean = false,
    isDeletingMode: Boolean = false,

    ) {
    if (questionsWithCheckBoxList.isNotEmpty()) {
        item(
            key = gradeTextRes
        ) {
            FavouriteQuestionCard(
                modifier = Modifier
                    .padding(
                        bottom = horizontalDividerVerticalPadding
                    )
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                onExpandCardTap()
                            }
                        )
                    }
                    .padding(
                        horizontal = horizontalFavQuestionsCardPadding,
                        vertical = verticalFavQuestionCardPadding
                    ),
                text = stringResource(gradeTextRes),
                shouldBeArrowInTheEnd = true,
                isExpanded = isExpanded,
                textFontColor = secondaryTextColor
            )
        }
        items(
            items = questionsWithCheckBoxList,
            key = {
                it.copy(isChecked = false).toString()
            }
        ) {

            AnimatedVisibility(isExpanded) {
                FavouriteQuestionCard(
                    modifier = Modifier
                        .padding(
                            bottom = horizontalDividerVerticalPadding
                        )
                        .fillMaxWidth()

                        .clip(RoundedCornerShape(clipParamsForQuestionWithAnswerBox))
                        .height(questionFavouriteCardHeight)
                        .background(color = cardColor)
                        .clickable {
                            if (isDeletingMode) {
                                onCheckBoxClicked(it)
                            } else {
                                onQuestionCardClicked(it.questionEntity)
                            }

                        }
                        .padding(
                            horizontal = questionWIthAnswerCardInnerPadding,
                            vertical = favouriteQuestionCardCategoryHorizontalPadding
                        ),
                    isDeletingMode = isDeletingMode,
                    checked = it.isChecked,
                    text = it.questionEntity.questionContent,
                    shouldBeArrowInTheEnd = false
                )
            }
        }
    }
}