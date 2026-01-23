package com.example.interviewaicoach.presentation.screens.questionsWithAnswersScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.theme.appBarIconColor
import com.example.interviewaicoach.presentation.theme.bottomButtonSaveQuestionScreenEndPadding
import com.example.interviewaicoach.presentation.theme.bottomButtonSaveQuestionScreenVerticalPadding
import com.example.interviewaicoach.presentation.theme.buttonsTextColor
import com.example.interviewaicoach.presentation.theme.clipParamsForBottomButtonOnQuestionsScreen
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.maxRecordingTimeFontSize
import com.example.interviewaicoach.presentation.theme.questionsWithAnswersBottomButtonsGradient
import com.example.interviewaicoach.presentation.theme.sizeOfIcons
import com.example.interviewaicoach.presentation.theme.widthBetweenIconAndTextInBottomButton


@Preview
@Composable
fun IconWithText(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    leftText: String = stringResource(R.string.save_question),
    rightText: String = "",
    colorOfIcon: Color = appBarIconColor,
    sizeOfIcon: Dp = sizeOfIcons,
    fontWeight: FontWeight = FontWeight.SemiBold,
    rightTextColor: Color = buttonsTextColor,
    leftTextColor: Color = buttonsTextColor,
    sizeOfText: Int = 14,
    contentAlignment: Alignment = Alignment.Center
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (imageVector != null) {
                Icon(
                    modifier = Modifier
                        .size(sizeOfIcon)
                        .then(
                            if (imageVector == Icons.AutoMirrored.Outlined.Logout) {
                                Modifier.rotate(180f)
                            } else Modifier
                        ),
                    imageVector = imageVector,
                    contentDescription = stringResource(R.string.exit_from_interview),
                    tint = colorOfIcon
                )
            }
            Spacer(modifier = Modifier.width(widthBetweenIconAndTextInBottomButton))
            Row(
                modifier = if (rightText.isEmpty()) Modifier else Modifier.fillMaxWidth(),
                horizontalArrangement = if (rightText.isEmpty()) Arrangement.Center else Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = leftText,
                    color = leftTextColor,
                    fontFamily = mainAppFontFamily,
                    fontSize = sizeOfText.sp,
                    fontWeight = fontWeight,
                )

                Text(
                    text = rightText,
                    fontFamily = mainAppFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = rightTextColor,
                    fontSize = maxRecordingTimeFontSize,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
    ) {
        IconWithText(
            modifier = Modifier
                .clip(RoundedCornerShape(clipParamsForBottomButtonOnQuestionsScreen))
                .background(
                    brush = questionsWithAnswersBottomButtonsGradient
                )
                .then(
                    Modifier.clickable(onClick = {

                    })

                )
                .padding(
                    vertical = bottomButtonSaveQuestionScreenVerticalPadding,
                    horizontal = bottomButtonSaveQuestionScreenEndPadding
                ),

            )
        Text("базовый")

        IconWithText(
            modifier = Modifier
                .clip(RoundedCornerShape(clipParamsForBottomButtonOnQuestionsScreen))
                .background(
                    brush = questionsWithAnswersBottomButtonsGradient
                )
                .then(
                    Modifier.clickable(onClick = {

                    })

                )
                .fillMaxWidth()
                .padding(
                    vertical = bottomButtonSaveQuestionScreenVerticalPadding,
                    horizontal = bottomButtonSaveQuestionScreenEndPadding
                ),

            )
        Text("на всю ширину")

        Row {
            IconWithText(
                Modifier
                    .clip(RoundedCornerShape(clipParamsForBottomButtonOnQuestionsScreen))
                    .background(
                        brush = questionsWithAnswersBottomButtonsGradient
                    )
                    .then(
                        Modifier.clickable(onClick = {

                        })

                    )
                    .weight(1f)
                    .padding(
                        vertical = bottomButtonSaveQuestionScreenVerticalPadding,

                        ),

                )
            IconWithText(
                Modifier
                    .clip(RoundedCornerShape(clipParamsForBottomButtonOnQuestionsScreen))
                    .background(
                        brush = questionsWithAnswersBottomButtonsGradient
                    )
                    .then(
                        Modifier.clickable(onClick = {

                        })

                    )
                    .weight(1f)
                    .padding(
                        vertical = bottomButtonSaveQuestionScreenVerticalPadding,
                    ),

                )
        }
        Text("2 вью на всю ширину экрана")
    }
}