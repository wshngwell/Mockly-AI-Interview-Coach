package com.example.interviewaicoach.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.correctAnswerScreenElements.IconWithText
import com.example.interviewaicoach.presentation.theme.clipParamsForAiResponseOption
import com.example.interviewaicoach.presentation.theme.clipParamsForQuestionWithAnswerBox
import com.example.interviewaicoach.presentation.theme.greenBrushForMainButton
import com.example.interviewaicoach.presentation.theme.horizontalDividerColor
import com.example.interviewaicoach.presentation.theme.horizontalPaddingOfResultScreenRating
import com.example.interviewaicoach.presentation.theme.lightIconColor
import com.example.interviewaicoach.presentation.theme.pink
import com.example.interviewaicoach.presentation.theme.questionAiFeedbackOptionsFontSize
import com.example.interviewaicoach.presentation.theme.questionWithAnswerCardColor
import com.example.interviewaicoach.presentation.theme.resultCardInnerPadding
import com.example.interviewaicoach.presentation.theme.resultCardTopOuterPadding
import com.example.interviewaicoach.presentation.theme.resultListItemsFontSize
import com.example.interviewaicoach.presentation.theme.resultScreenHorizontalDividerVerticalPadding
import com.example.interviewaicoach.presentation.theme.transparentGradientBrush
import com.example.interviewaicoach.presentation.theme.verticalPaddingOfAiFeedBackOptions

@Preview
@Composable
fun FinalResultsCard(
    modifier: Modifier = Modifier,
    averageScore: Int = 9,
    numberOfQuestions: Int = 48,
    numberOfVoiceAnsweredQuestions: Int = 32,
    numberOfSavedQuestions: Int = 16
) {
    Column(
        modifier = modifier
            .padding(
                top = resultCardTopOuterPadding,
            )
            .fillMaxWidth()
            .background(
                color = questionWithAnswerCardColor,
                shape = RoundedCornerShape(clipParamsForQuestionWithAnswerBox)
            )
            .padding(resultCardInnerPadding)

    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconWithText(
                modifier = Modifier
                    .background(
                        brush = transparentGradientBrush
                    ),
                imageVector = Icons.Filled.AutoAwesome,
                leftText = stringResource(R.string.final_points),
                colorOfIcon = pink,
                sizeOfText = resultListItemsFontSize
            )
            MainAppButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(clipParamsForAiResponseOption))
                    .background(brush = greenBrushForMainButton)
                    .padding(
                        vertical = verticalPaddingOfAiFeedBackOptions,
                        horizontal = horizontalPaddingOfResultScreenRating
                    ),
                text = stringResource(R.string.points_from_10, averageScore),
                fontSize = questionAiFeedbackOptionsFontSize,
                fontWeight = FontWeight.SemiBold
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = resultScreenHorizontalDividerVerticalPadding),
            thickness = 1.dp,
            color = horizontalDividerColor
        )
        IconWithText(
            modifier = Modifier
                .fillMaxWidth(),
            imageVector = Icons.Outlined.Task,
            leftText = stringResource(R.string.learned_numberOfQuestions, numberOfQuestions),
            colorOfIcon = lightIconColor,
            sizeOfText = resultListItemsFontSize,
            contentAlignment = Alignment.TopStart
        )
        Spacer(modifier = Modifier.height(24.dp))

        IconWithText(
            modifier = Modifier
                .fillMaxWidth(),
            imageVector = Icons.Outlined.Mic,
            leftText = stringResource(
                R.string.on_this_number_of_questions_answered_with_voice,
                numberOfVoiceAnsweredQuestions
            ),
            colorOfIcon = lightIconColor,
            sizeOfText = resultListItemsFontSize,
            contentAlignment = Alignment.TopStart
        )

        Spacer(modifier = Modifier.height(24.dp))

        IconWithText(
            modifier = Modifier
                .fillMaxWidth(),
            imageVector = Icons.Outlined.BookmarkBorder,
            leftText = stringResource(
                R.string.you_saved_this_numberOfQuestions,
                numberOfSavedQuestions
            ),
            colorOfIcon = lightIconColor,
            sizeOfText = resultListItemsFontSize,
            contentAlignment = Alignment.TopStart
        )
    }
}