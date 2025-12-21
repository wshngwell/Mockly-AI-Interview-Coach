package com.example.interviewaicoach.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.interviewaicoach.presentation.theme.borderOfSelectedInterviewParam
import com.example.interviewaicoach.presentation.theme.borderOfUnselectedInterviewParam
import com.example.interviewaicoach.presentation.theme.buttonsTextColor
import com.example.interviewaicoach.presentation.theme.clipParamsForMainButtons
import com.example.interviewaicoach.presentation.theme.disabledColorForTextForMainButton
import com.example.interviewaicoach.presentation.theme.interviewParamsBoxColor
import com.example.interviewaicoach.presentation.theme.disabledGradientBrushForMainButton
import com.example.interviewaicoach.presentation.theme.gradientBrushForMainButton
import com.example.interviewaicoach.presentation.theme.myBackGround
import com.example.interviewaicoach.presentation.theme.paramsForMainButtonPadding
import com.example.interviewaicoach.presentation.theme.transparentGradientBrush


@Composable
fun OptionsForInterViewBox(
    listOfInterviewParamsStringsIds: List<Int> = listOf(),
    interviewParamsButtonText: String = "",
    onClickOnInterviewParamsButton: (String) -> Unit = {},
    onParamClicked: (String) -> Unit = {},
    currentSelectedOption: String = ""

) {

    val context = LocalContext.current

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .background(myBackGround)
            .fillMaxWidth()

    ) {
        Column(
            modifier = Modifier
                .background(myBackGround)
                .fillMaxWidth()
                .verticalScroll(scrollState)

        ) {
            listOfInterviewParamsStringsIds.forEachIndexed { index, stringId ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(clipParamsForMainButtons))
                        .background(color = interviewParamsBoxColor)
                        .border(
                            width = if (currentSelectedOption == context.getString(stringId)) borderOfSelectedInterviewParam else borderOfUnselectedInterviewParam,
                            brush = if (currentSelectedOption == context.getString(stringId)) gradientBrushForMainButton else transparentGradientBrush,
                            shape = RoundedCornerShape(clipParamsForMainButtons)
                        )
                        .clickable { onParamClicked(context.getString(stringId)) }
                        .padding(paramsForMainButtonPadding)
                )
                {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = context.getString(stringId),
                        textAlign = TextAlign.Center,
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))

            }
            Spacer(modifier = Modifier.height(20.dp))

            val enabled = currentSelectedOption.isNotEmpty()

            MainAppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(clipParamsForMainButtons))
                    .background(
                        brush = if (enabled) gradientBrushForMainButton else
                            disabledGradientBrushForMainButton
                    )
                    .then(
                        if (enabled) {
                            Modifier.clickable(onClick = {
                                onClickOnInterviewParamsButton(currentSelectedOption)
                            })
                        } else {
                            Modifier
                        }
                    )
                    .padding(paramsForMainButtonPadding),
                text = interviewParamsButtonText,
                fontColor = if (enabled) buttonsTextColor else disabledColorForTextForMainButton
            )
        }

    }
}