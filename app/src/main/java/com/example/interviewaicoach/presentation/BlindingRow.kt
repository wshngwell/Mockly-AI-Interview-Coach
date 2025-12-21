package com.example.interviewaicoach.presentation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.interviewaicoach.presentation.theme.heightOfSkeletonRow
import com.example.interviewaicoach.presentation.theme.lightQuestionsWithAnswersBottomButtonsColor
import com.example.interviewaicoach.presentation.theme.questionsWithAnswersBottomButtonsColor


@Preview
@Composable
fun BlindingRow(
    width: Dp = 100.dp
) {
    val colorAnimation = rememberInfiniteTransition(label = "")
    val color by colorAnimation.animateColor(
        initialValue = questionsWithAnswersBottomButtonsColor,
        targetValue = lightQuestionsWithAnswersBottomButtonsColor,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Row(
        modifier = Modifier
            .height(heightOfSkeletonRow)
            .width(width)
            .clip(RoundedCornerShape(heightOfSkeletonRow))
            .background(color)
    ) {
    }
}