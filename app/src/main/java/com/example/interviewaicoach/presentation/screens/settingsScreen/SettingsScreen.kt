package com.example.interviewaicoach.presentation.screens.settingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.screens.destinations.ChooseDirectionScreenDestination
import com.example.interviewaicoach.presentation.screens.questionsWithAnswersScreen.QuestionsNavBar
import com.example.interviewaicoach.presentation.theme.myBackGround
import com.example.interviewaicoach.presentation.theme.questionWIthAnswerCardTopOuterPadding
import com.example.interviewaicoach.presentation.theme.screenBottomAdditionalPadding
import com.example.interviewaicoach.presentation.theme.screenHorizontalPadding
import com.example.interviewaicoach.presentation.theme.screenTopPadding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@RootNavGraph
@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator
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
            text = stringResource(R.string.settings),
            onLeftIconClicked = {
                navigator.navigate(ChooseDirectionScreenDestination) {
                    launchSingleTop = true
                    popUpTo(ChooseDirectionScreenDestination)
                }
            },
        )

        Column(
            modifier = Modifier
                .padding(top = questionWIthAnswerCardTopOuterPadding)
                .fillMaxSize()
        ) {
            SettingsCard()
        }
    }
}