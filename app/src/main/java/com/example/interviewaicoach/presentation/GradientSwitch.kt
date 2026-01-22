package com.example.interviewaicoach.presentation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interviewaicoach.presentation.theme.blackGradient
import com.example.interviewaicoach.presentation.theme.gap
import com.example.interviewaicoach.presentation.theme.gradientBrushForMainButton
import com.example.interviewaicoach.presentation.theme.heightOfSwitch
import com.example.interviewaicoach.presentation.theme.thumbSize
import com.example.interviewaicoach.presentation.theme.widthOfSwitch

@Preview
@Composable
fun GradientSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean = true,
    onCheckedChange: () -> Unit = {}
) {


    val thumbOffset by animateDpAsState(
        targetValue = if (checked) widthOfSwitch - thumbSize - gap else gap,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
    )

    Box(
        modifier = modifier
            .width(widthOfSwitch)
            .height(heightOfSwitch)
            .clip(CircleShape)
            .background(
                if (checked) gradientBrushForMainButton else blackGradient
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onCheckedChange()
            }
            .padding(vertical = gap),
        contentAlignment = Alignment.CenterStart
    ) {

        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .size(thumbSize)
                .shadow(4.dp, CircleShape)
                .background(Color.White, CircleShape)
        )
    }
}