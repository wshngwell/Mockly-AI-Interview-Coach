package com.example.interviewaicoach.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


private val backGroundLight = Color(0xFFEFF1F4)
private val backGroundDark = Color.Black

val myBackGround
    get() = if (currentTheme.value.isSystemDark) {
        backGroundDark
    } else {
        backGroundLight
    }


val cardColor
    get() = if (currentTheme.value.isSystemDark) {
        Color(0xFF1D1D1D)
    } else {
        Color.White
    }
val secondaryTextColor
    get() = if (currentTheme.value.isSystemDark) {
        Color(0xFF7E7E7E)
    } else {
        Color(0xFF808C99)
    }

val buttonsTextColor = Color.White

val bottomBarIconColor = buttonsTextColor

val appBarIconColor
    get() = if (currentTheme.value.isSystemDark) {
        bottomBarIconColor
    } else {
        backGroundDark
    }

val primaryTextColor
    get() = if (currentTheme.value.isSystemDark) {
        buttonsTextColor
    } else {
        Color(0xFF1A1A1A)
    }


val horizontalDividerColor
    get() = if (currentTheme.value.isSystemDark) {
        Color(0xFF3A3A3A)
    } else {
        Color(0xFFD9D9D9)
    }

val redIconColor = Color(0xFFBD0000)


val lightGreenTextColor
    get() = if (currentTheme.value.isSystemDark) {
        Color(0xFF079256)
    } else {
        Color(0xFF137307)
    }
val lightRedTextColor
    get() = if (currentTheme.value.isSystemDark) {
        Color(0xFFFA9292)
    } else {
        Color(0xFFCC5500)
    }

val pink = Color(0xFFD758A7)
val checkBoxBorderColor = Color(0xFF3A3A3A)

val gradientBrushForMainButton = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF9C6CCF),
        pink
    )
)

val disabledGradientBrushForMainButton
    get() = if (currentTheme.value.isSystemDark) {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF1D1D1D),
                Color(0xFF1D1D1D),
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFDDE1E5),
                Color(0xFFDDE1E5),
            )
        )
    }


val blackGradient = Brush.horizontalGradient(
    colors = listOf(
        secondaryTextColor,
        secondaryTextColor,
    )
)

val questionsWithAnswersBottomButtonsColor
    get() = if (currentTheme.value.isSystemDark) {
        Color(0xFF282828)
    } else {
        Color(0xFFDDE1E5)
    }
val deleteAllFontColor = Color(0xFFF16060)
val lightQuestionsWithAnswersBottomButtonsColor
    get() = if (currentTheme.value.isSystemDark) {
        Color(0xFF505050)
    } else {
        Color(0xFF808C99)
    }
val questionsWithAnswersBottomButtonsGradient
    get() = if (currentTheme.value.isSystemDark) {
        Brush.horizontalGradient(
            colors = listOf(
                questionsWithAnswersBottomButtonsColor,
                questionsWithAnswersBottomButtonsColor
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF808C99),
                Color(0xFF808C99),
            )
        )
    }
val transparentGradientBrush = Brush.horizontalGradient(
    colors = listOf(
        Color.Transparent,
        Color.Transparent
    )
)

val ratingResultBrushForMainButton = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF006E2E),
        Color(0xFF006E2E),
    )
)
val positiveAspectsBrushForMainButton
    get() = if (currentTheme.value.isSystemDark) {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF0D2B1A),
                Color(0xFF0D2B1A)
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFD8FDEA),
                Color(0xFFD8FDEA),
            )
        )
    }

val disabledColorForTextForMainButton
    get() = if (currentTheme.value.isSystemDark) {
        Color(0xFF5C5C5C)
    } else {
        Color(0xFFB8C0CA)
    }

val redBrushForMainButton
    get() = if (currentTheme.value.isSystemDark) {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF582121),
                Color(0xFF582121),
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFFFECC2),
                Color(0xFFFFECC2),
            )
        )
    }
