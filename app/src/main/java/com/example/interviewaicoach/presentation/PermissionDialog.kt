package com.example.interviewaicoach.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.theme.alertDialogColor
import com.example.interviewaicoach.presentation.theme.alertDialogDismissButtonColor
import com.example.interviewaicoach.presentation.theme.alertDialogInnerPadding
import com.example.interviewaicoach.presentation.theme.alertDialogTextOnButtonsFontSize
import com.example.interviewaicoach.presentation.theme.alertDialogTitleFontSize
import com.example.interviewaicoach.presentation.theme.answersFontSize
import com.example.interviewaicoach.presentation.theme.buttonsTextColor
import com.example.interviewaicoach.presentation.theme.clipParamsForBottomButtonOnQuestionsScreen
import com.example.interviewaicoach.presentation.theme.clipParamsForMainButtons
import com.example.interviewaicoach.presentation.theme.darkThemeTextColor
import com.example.interviewaicoach.presentation.theme.gradientBrushForMainButton
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.paramsForMainButtonPadding

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PermissionDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    confirmButtonText: String = stringResource(R.string.try_again),
    dismissButtonText: String = stringResource(R.string.close),
    titleText: String = stringResource(R.string.error),
    descriptionText: String = stringResource(R.string.ai_error)
) {

    BasicAlertDialog(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(clipParamsForBottomButtonOnQuestionsScreen))
            .background(alertDialogColor)
            .padding(alertDialogInnerPadding),
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = titleText,
                fontFamily = mainAppFontFamily,
                fontWeight = FontWeight.Bold,
                color = darkThemeTextColor,
                fontSize = alertDialogTitleFontSize
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = descriptionText,
                fontFamily = mainAppFontFamily,
                fontWeight = FontWeight.Medium,
                color = darkThemeTextColor,
                fontSize = answersFontSize
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceAround

                ) {
                MainAppButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(clipParamsForMainButtons))
                        .background(alertDialogDismissButtonColor)
                        .clickable { onDismiss() }
                        .padding(paramsForMainButtonPadding),
                    text = dismissButtonText,
                    fontColor = buttonsTextColor,
                    fontSize = alertDialogTextOnButtonsFontSize
                )

                Spacer(modifier = Modifier.width(14.dp))

                MainAppButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(clipParamsForMainButtons))
                        .background(gradientBrushForMainButton)
                        .clickable { onConfirm() }
                        .padding(paramsForMainButtonPadding),
                    text = confirmButtonText,
                    fontColor = buttonsTextColor,
                    fontSize = alertDialogTextOnButtonsFontSize
                )

            }

        }
    }
}