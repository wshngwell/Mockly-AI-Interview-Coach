package com.example.interviewaicoach.presentation.screens.questionsWithAnswersScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interviewaicoach.R
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerAiFeedbackEntity
import com.example.interviewaicoach.presentation.screens.chooseDirectionScreen.MainAppButton
import com.example.interviewaicoach.presentation.utils.mockAnswerAiFeedbackEntity
import com.example.interviewaicoach.presentation.theme.aiFeedbackTextFontSize
import com.example.interviewaicoach.presentation.theme.clipParamsForAiResponseOption
import com.example.interviewaicoach.presentation.theme.horizontalDividerColor
import com.example.interviewaicoach.presentation.theme.horizontalPaddingOfAiFeedBackOptions
import com.example.interviewaicoach.presentation.theme.lightGreenTextColor
import com.example.interviewaicoach.presentation.theme.lightRedTextColor
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.pink
import com.example.interviewaicoach.presentation.theme.positiveAspectsBrushForMainButton
import com.example.interviewaicoach.presentation.theme.primaryTextColor
import com.example.interviewaicoach.presentation.theme.questionAiFeedbackOptionsFontSize
import com.example.interviewaicoach.presentation.theme.ratingResultBrushForMainButton
import com.example.interviewaicoach.presentation.theme.redBrushForMainButton
import com.example.interviewaicoach.presentation.theme.transparentGradientBrush
import com.example.interviewaicoach.presentation.theme.verticalPaddingOfAiFeedBackOptions


@Preview
@Composable
fun AiFeedbackOnAnswer(
    answerFromAi: AnswerAiFeedbackEntity = mockAnswerAiFeedbackEntity,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconWithText(
            modifier = Modifier
                .background(
                    brush = transparentGradientBrush
                ),
            imageVector = Icons.Filled.AutoAwesome,
            leftText = stringResource(R.string.ai_insights),
            rightTextColor = primaryTextColor,
            colorOfIcon = pink
        )
        if (answerFromAi.ratingFromAi > 5) {
            MainAppButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(clipParamsForAiResponseOption))
                    .background(brush = ratingResultBrushForMainButton)
                    .padding(
                        vertical = verticalPaddingOfAiFeedBackOptions,
                        horizontal = horizontalPaddingOfAiFeedBackOptions
                    ),
                text = stringResource(R.string.good_answer),
                fontSize = questionAiFeedbackOptionsFontSize,
                fontWeight = FontWeight.SemiBold
            )
        } else {
            MainAppButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(clipParamsForAiResponseOption))
                    .background(brush = redBrushForMainButton)
                    .padding(
                        vertical = verticalPaddingOfAiFeedBackOptions,
                        horizontal = horizontalPaddingOfAiFeedBackOptions
                    ),
                fontColor = lightRedTextColor,
                text = stringResource(R.string.bad_answer),
                fontSize = questionAiFeedbackOptionsFontSize,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
    Spacer(modifier = Modifier.height(24.dp))

    MainAppButton(
        modifier = Modifier
            .clip(RoundedCornerShape(clipParamsForAiResponseOption))
            .background(brush = positiveAspectsBrushForMainButton)
            .padding(
                vertical = verticalPaddingOfAiFeedBackOptions,
                horizontal = horizontalPaddingOfAiFeedBackOptions
            ),
        text = stringResource(R.string.good_part),
        fontSize = questionAiFeedbackOptionsFontSize,
        fontWeight = FontWeight.Bold,
        fontColor = lightGreenTextColor
    )
    Spacer(modifier = Modifier.height(8.dp))

    answerFromAi.goodPartAnswer.forEach {

        Text(
            text = "• $it",
            fontSize = aiFeedbackTextFontSize,
            fontFamily = mainAppFontFamily,
            color = primaryTextColor,
            fontWeight = FontWeight.Medium
        )
    }

    Spacer(modifier = Modifier.height(24.dp))

    MainAppButton(
        modifier = Modifier
            .clip(RoundedCornerShape(clipParamsForAiResponseOption))
            .background(brush = redBrushForMainButton)
            .padding(
                vertical = verticalPaddingOfAiFeedBackOptions,
                horizontal = horizontalPaddingOfAiFeedBackOptions
            ),
        text = stringResource(R.string.negative_part),
        fontSize = questionAiFeedbackOptionsFontSize,
        fontWeight = FontWeight.SemiBold,
        fontColor = lightRedTextColor
    )

    Spacer(modifier = Modifier.height(8.dp))

    answerFromAi.badPartAnswer.forEach {

        Text(
            text = "• $it",
            color = primaryTextColor,
            fontFamily = mainAppFontFamily,
            fontSize = aiFeedbackTextFontSize,
            fontWeight = FontWeight.Medium
        )
    }

    Spacer(modifier = Modifier.height(14.dp))

    HorizontalDivider(
        thickness = 1.dp,
        color = horizontalDividerColor
    )
    Spacer(modifier = Modifier.height(13.dp))

}