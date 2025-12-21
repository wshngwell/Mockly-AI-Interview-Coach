package com.example.interviewaicoach.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.theme.answersFontSize
import com.example.interviewaicoach.presentation.theme.buttonsTextColor
import com.example.interviewaicoach.presentation.theme.clipParamsForMainButtons
import com.example.interviewaicoach.presentation.theme.darkThemeTextColor
import com.example.interviewaicoach.presentation.theme.gradientBrushForMainButton
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.networkErrorIconHeight
import com.example.interviewaicoach.presentation.theme.networkErrorIconWidth
import com.example.interviewaicoach.presentation.theme.paramsForMainButtonPadding
import com.example.interviewaicoach.presentation.theme.questionFontSize
import com.example.interviewaicoach.presentation.theme.unselectedBottomBarElementColor

@Preview
@Composable
fun ErrorCard(
    modifier: Modifier = Modifier,
    errorTextId: Int = R.string.generation_error,
    errorDescriptionId: Int = R.string.generationError_desc,
    imageResourceId: Int = R.drawable.generation_error,
    buttonText: String = stringResource(R.string.retry_error),
    onButtonClicked: () -> Unit = {},
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(networkErrorIconWidth)
                .height(networkErrorIconHeight),
            painter = painterResource(imageResourceId),
            contentDescription = stringResource(R.string.generation_error_icon)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(errorTextId),
            fontFamily = mainAppFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = darkThemeTextColor,
            fontSize = questionFontSize,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(errorDescriptionId),
            fontFamily = mainAppFontFamily,
            fontWeight = FontWeight.Medium,
            color = unselectedBottomBarElementColor,
            fontSize = answersFontSize,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        MainAppButton(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(clipParamsForMainButtons))
                .background(
                    brush = gradientBrushForMainButton
                )
                .clickable {
                    onButtonClicked()
                }
                .padding(paramsForMainButtonPadding),
            text = buttonText,
            fontColor = buttonsTextColor
        )
    }
}