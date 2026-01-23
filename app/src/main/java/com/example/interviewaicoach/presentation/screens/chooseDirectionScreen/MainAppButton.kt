package com.example.interviewaicoach.presentation.screens.chooseDirectionScreen


import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.interviewaicoach.presentation.theme.buttonsTextColor
import com.example.interviewaicoach.presentation.theme.interViewButtonParamsFontSize
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily

@Composable
fun MainAppButton(
    modifier: Modifier = Modifier,
    text: String = "",
    fontSize: Int = interViewButtonParamsFontSize,
    fontWeight: FontWeight = FontWeight.Medium,
    fontColor: Color = buttonsTextColor
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = fontColor,
            fontFamily = mainAppFontFamily,
            fontSize = fontSize.sp,
            fontWeight = fontWeight
        )
    }
}