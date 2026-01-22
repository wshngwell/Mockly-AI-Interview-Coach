package com.example.interviewaicoach.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.correctAnswerScreenElements.IconWithText
import com.example.interviewaicoach.presentation.correctAnswerScreenElements.TextWithIcon
import com.example.interviewaicoach.presentation.theme.bottomBarIconColor
import com.example.interviewaicoach.presentation.theme.bottomButtonSaveQuestionScreenVerticalPadding
import com.example.interviewaicoach.presentation.theme.bottomElementsCorrectAnswerScreenVerticalPadding
import com.example.interviewaicoach.presentation.theme.clipParamsForBottomButtonOnQuestionsScreen
import com.example.interviewaicoach.presentation.theme.disabledColorForTextForMainButton
import com.example.interviewaicoach.presentation.theme.disabledGradientBrushForMainButton
import com.example.interviewaicoach.presentation.theme.gradientBrushForMainButton
import com.example.interviewaicoach.presentation.theme.primaryTextColor
import com.example.interviewaicoach.presentation.theme.sizeOfIcons
import com.example.interviewaicoach.presentation.theme.transparentGradientBrush


@Preview
@Composable
fun BottomElementsBar(
    modifier: Modifier = Modifier,
    onLeftButtonClicked: () -> Unit = {},
    onRightButtonClicked: () -> Unit = {},
    shouldBeButtonsDisabled: Boolean = false,
    rightIcon: ImageVector = Icons.Outlined.ArrowForward,
    leftIcon: ImageVector = Icons.Outlined.BookmarkBorder,
    rightElementText: String = stringResource(R.string.next_question),
    leftElementSecondText: String = stringResource(R.string.max_recording_time),
    leftElementText: String = stringResource(R.string.save_question),
    leftIconColor: Color = bottomBarIconColor,
    rightIconColor: Color = bottomBarIconColor,
    rightTextColor: Color = bottomBarIconColor,
    leftButtonBrush: Brush = gradientBrushForMainButton,
    rightButtonBrush: Brush = gradientBrushForMainButton,
    widthOfBorderDp: Dp = 0.dp,
    brushForBorder: Brush = transparentGradientBrush,
    shapeForBorder: Shape = RoundedCornerShape(0.dp),
    isRecording: Boolean = false,
    sizeOfLeftIcon: Dp = sizeOfIcons,
    isIconSecondButtonLeft: Boolean = false,
    answeringFromFavouriteMode: Boolean = false,
) {
    Row(
        modifier = modifier
            .padding(
                top = bottomElementsCorrectAnswerScreenVerticalPadding
            )
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {


        IconWithText(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(
                    width = widthOfBorderDp,
                    brush = brushForBorder,
                    shape = shapeForBorder
                )
                .then(
                    if (!isRecording) Modifier.clip(
                        RoundedCornerShape(
                            clipParamsForBottomButtonOnQuestionsScreen
                        )
                    ) else Modifier

                )
                .background(
                    brush = if (shouldBeButtonsDisabled) disabledGradientBrushForMainButton else
                        leftButtonBrush
                )
                .then(
                    if (shouldBeButtonsDisabled || isRecording) modifier else
                        Modifier.clickable(onClick = {
                            onLeftButtonClicked()
                        })

                )
                .padding(
                    bottomButtonSaveQuestionScreenVerticalPadding
                ),
            imageVector = leftIcon,
            leftText = leftElementText,
            leftTextColor = if (isRecording)
                primaryTextColor else if (shouldBeButtonsDisabled) disabledColorForTextForMainButton
            else bottomBarIconColor,
            colorOfIcon = if (shouldBeButtonsDisabled) disabledColorForTextForMainButton else leftIconColor,
            rightTextColor = disabledColorForTextForMainButton,
            sizeOfIcon = sizeOfLeftIcon,
            rightText = if (isRecording) leftElementSecondText else ""
        )
        Spacer(modifier = Modifier.width(14.dp))

        val secondButtonModifier = Modifier
            .then(
                if (isRecording) Modifier else Modifier.weight(1f)
            )
            .fillMaxHeight()
            .clip(
                RoundedCornerShape(
                    clipParamsForBottomButtonOnQuestionsScreen
                )
            )
            .background(
                brush = if (shouldBeButtonsDisabled) disabledGradientBrushForMainButton else
                    rightButtonBrush
            )
            .then(
                if (shouldBeButtonsDisabled) modifier else
                    Modifier.clickable(onClick = {
                        onRightButtonClicked()
                    })

            )
            .padding(
                bottomButtonSaveQuestionScreenVerticalPadding
            )
        if (isIconSecondButtonLeft && !answeringFromFavouriteMode) {
            IconWithText(
                modifier = secondButtonModifier,
                imageVector = rightIcon,
                leftText = rightElementText,
                colorOfIcon = if (shouldBeButtonsDisabled) disabledColorForTextForMainButton else rightIconColor,
                leftTextColor = if (shouldBeButtonsDisabled) disabledColorForTextForMainButton else rightTextColor,
            )
        } else if (answeringFromFavouriteMode) {
            IconWithText(
                modifier = secondButtonModifier,
                leftText = rightElementText,
                colorOfIcon = if (shouldBeButtonsDisabled) disabledColorForTextForMainButton else rightIconColor,
                leftTextColor = if (shouldBeButtonsDisabled) disabledColorForTextForMainButton else rightTextColor,
            )
        } else {
            TextWithIcon(
                modifier = secondButtonModifier,
                imageVector = rightIcon,
                textOnButton = rightElementText,
                colorOfIcon = if (shouldBeButtonsDisabled) disabledColorForTextForMainButton else rightIconColor,
                textColor = if (shouldBeButtonsDisabled) disabledColorForTextForMainButton else rightTextColor,
            )
        }
    }
}