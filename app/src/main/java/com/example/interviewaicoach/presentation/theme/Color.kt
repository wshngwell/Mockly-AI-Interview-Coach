package com.example.interviewaicoach.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val bottomBarBackgroundColor = Color(0xFF282828)
val questionWithAnswerCardColor = bottomBarBackgroundColor

val selectedBottomElementBarColor = Color.White
val unselectedBottomBarElementColor = Color(0xFF7E7E7E)
val favCategoryArrowColor = Color(0xFF7E7E7E)
val numberAndTopicOfQuestionColor = unselectedBottomBarElementColor

val myBackGround
    get() = if (currentTheme.value.isSystemDark) {
        backGroundDark
    } else {
        backGroundLight
    }

val buttonsTextColor = selectedBottomElementBarColor
val darkThemeTextColor = buttonsTextColor
val horizontalDividerColor = Color(0xFF3A3A3A)

val lightIconColor = selectedBottomElementBarColor
val redIconColor = Color.Red


private val backGroundLight = Color.White
private val backGroundDark = Color.Black

val interviewParamsBoxColor = Color(0xFF1A1A1A)
val lightGreenTextColor = Color(0xFF079256)
val lightRedTextColor = Color(0xFFFA9292)
val pink = Color(0xFFD758A7)

val gradientBrushForMainButton = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF9C6CCF),
        pink
    )
)
val disabledGradientBrushForMainButton = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF282828),
        Color(0xFF282828),
    )
)

val questionsWithAnswersBottomButtonsColor = Color(0xFF282828)
val lightQuestionsWithAnswersBottomButtonsColor = Color(0xFF505050)
val questionsWithAnswersBottomButtonsGradient = Brush.horizontalGradient(
    colors = listOf(
        questionsWithAnswersBottomButtonsColor,
        questionsWithAnswersBottomButtonsColor
    )
)
val transparentGradientBrush = Brush.horizontalGradient(
    colors = listOf(
        Color.Transparent,
        Color.Transparent
    )
)

val greenBrushForMainButton = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF006E2E),
        Color(0xFF006E2E),
    )
)
val darkGreenBrushForMainButton = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF0D2B1A),
        Color(0xFF0D2B1A)
    )
)
val disabledColorForTextForMainButton = Color(0xFF5C5C5C)

val redBrushForMainButton = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF582121),
        Color(0xFF582121),
    )
)

val alertDialogColor = Color(0xFF1D1D1D)
val alertDialogDismissButtonColor = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF282828),
        Color(0xFF282828)
    )
)
