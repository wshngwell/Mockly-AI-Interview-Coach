package com.example.interviewaicoach.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interviewaicoach.domain.entities.LoadingException
import com.example.interviewaicoach.domain.entities.questionsWithAnswersEntities.AnswerAiFeedbackEntity
import com.example.interviewaicoach.presentation.theme.answersFontSize
import com.example.interviewaicoach.presentation.theme.clipParamsForQuestionWithAnswerBox
import com.example.interviewaicoach.presentation.theme.darkThemeTextColor
import com.example.interviewaicoach.presentation.theme.horizontalDividerColor
import com.example.interviewaicoach.presentation.theme.horizontalDividerVerticalPadding
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.numberAndTopicOfQuestionBottomPadding
import com.example.interviewaicoach.presentation.theme.numberAndTopicOfQuestionColor
import com.example.interviewaicoach.presentation.theme.numberAndTopicOfQuestionFontSize
import com.example.interviewaicoach.presentation.theme.questionFontSize
import com.example.interviewaicoach.presentation.theme.questionWIthAnswerCardInnerPadding
import com.example.interviewaicoach.presentation.theme.questionWIthAnswerCardTopOuterPadding
import com.example.interviewaicoach.presentation.theme.questionWithAnswerCardColor

@Preview
@Composable
fun ColumnScope.QuestionWithAnswerCard(
    modifier: Modifier = Modifier,
    error: LoadingException? = null,
    isQuestionVisible: Boolean = false,
    isCorrectAnswerVisible: Boolean = false,
    isAiFeedBackVisible: Boolean = false,
    topicOfQuestionText: String = mockTopicOfAuestionText,
    shouldBeAiFeedbackBeShown: Boolean = false,
    shouldBeCorrectAnswerBeShown: Boolean = false,
    questionText: String = mockQuesstionText,
    correctAnswer: String = mockAnswerText,
    answerFromAi: AnswerAiFeedbackEntity = mockAnswerAiFeedbackEntity,
    onRetryAfterError: () -> Unit = {}
) {
    val mainContentScrollState = rememberScrollState()
    val errorScrollState = rememberScrollState()

    Box(
        modifier = modifier
            .padding(
                top = questionWIthAnswerCardTopOuterPadding,
            )
            .fillMaxWidth()
            .weight(1f)
            .background(
                color = questionWithAnswerCardColor,
                shape = RoundedCornerShape(
                    topStart = clipParamsForQuestionWithAnswerBox,
                    topEnd = clipParamsForQuestionWithAnswerBox
                )
            )
            .padding(questionWIthAnswerCardInnerPadding)

    ) {
        if (error != null) {
            Column(
                modifier.verticalScroll(errorScrollState)
            ) {
                ErrorCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 36.dp,
                            horizontal = 30.dp
                        ),
                    onButtonClicked = { onRetryAfterError() },
                    errorTextId = error.parseLoadingExceptionToNameErrorStringResource(),
                    errorDescriptionId = error.parseLoadingExceptionToDescriptionErrorStringResource(),
                    imageResourceId = error.parseLoadingExceptionToImageErrorResource()
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .verticalScroll(mainContentScrollState)
            ) {
                if (!isQuestionVisible) {
                    BlindingRow(
                        width = 170.dp
                    )

                } else {
                    Text(
                        modifier = Modifier.padding(bottom = numberAndTopicOfQuestionBottomPadding),
                        text = topicOfQuestionText,
                        fontFamily = mainAppFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = numberAndTopicOfQuestionColor,
                        fontSize = numberAndTopicOfQuestionFontSize

                    )
                }
                if (!isQuestionVisible) {

                    Spacer(modifier = Modifier.height(20.dp))
                    BlindingRow(
                        width = 270.dp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    BlindingRow(
                        width = 70.dp
                    )

                } else {
                    Text(
                        text = questionText,
                        fontFamily = mainAppFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = darkThemeTextColor,
                        fontSize = questionFontSize
                    )
                }
                if (shouldBeAiFeedbackBeShown) {
                    if (!isAiFeedBackVisible) {

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = horizontalDividerVerticalPadding),
                            thickness = 1.dp,
                            color = horizontalDividerColor
                        )
                        PackOfBlindingRows()
                        Spacer(modifier = Modifier.height(10.dp))
                        PackOfBlindingRows()

                    } else {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = horizontalDividerVerticalPadding),
                            thickness = 1.dp,
                            color = horizontalDividerColor
                        )
                        AiFeedbackOnAnswer(
                            answerFromAi = answerFromAi
                        )
                        if (!isCorrectAnswerVisible) {
                            PackOfBlindingRows()
                            Spacer(modifier = Modifier.height(10.dp))
                            PackOfBlindingRows()

                        } else {
                            Text(
                                text = correctAnswer,
                                fontFamily = mainAppFontFamily,
                                fontWeight = FontWeight.Medium,
                                color = darkThemeTextColor,
                                fontSize = answersFontSize
                            )
                        }
                    }

                } else if (shouldBeCorrectAnswerBeShown) {
                    if (!isCorrectAnswerVisible) {

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = horizontalDividerVerticalPadding),
                            thickness = 1.dp,
                            color = horizontalDividerColor
                        )
                        PackOfBlindingRows()
                        Spacer(modifier = Modifier.height(10.dp))
                        PackOfBlindingRows()


                    } else {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = horizontalDividerVerticalPadding),
                            thickness = 1.dp,
                            color = horizontalDividerColor
                        )
                        Text(
                            text = correctAnswer,
                            fontFamily = mainAppFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = darkThemeTextColor,
                            fontSize = answersFontSize
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PackOfBlindingRows() {
    BlindingRow(
        width = 170.dp
    )
    Spacer(modifier = Modifier.height(10.dp))
    BlindingRow(
        width = 270.dp
    )
    Spacer(modifier = Modifier.height(10.dp))
    BlindingRow(
        width = 70.dp
    )
}