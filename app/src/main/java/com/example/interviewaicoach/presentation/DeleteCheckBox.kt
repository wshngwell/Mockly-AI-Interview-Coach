package com.example.interviewaicoach.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import com.example.interviewaicoach.presentation.theme.cardColor
import com.example.interviewaicoach.presentation.theme.checkBoxBorderColor
import com.example.interviewaicoach.presentation.theme.checkBoxRadiusBorder
import com.example.interviewaicoach.presentation.theme.checkBoxRadiusBorderSize
import com.example.interviewaicoach.presentation.theme.gradientBrushForMainButton
import com.example.interviewaicoach.presentation.theme.sizeOfCheckIcon
import com.example.interviewaicoach.presentation.theme.sizeOfIcons

@Composable
fun DeleteCheckbox(
    checked: Boolean,
    modifier: Modifier = Modifier
) {

    val checkAlpha by animateFloatAsState(
        targetValue = if (checked) 1f else 0f
    )

    val borderColor by animateColorAsState(
        targetValue = if (checked) Color.Transparent else checkBoxBorderColor,
    )

    Box(
        modifier = modifier
            .size(sizeOfIcons)
            .clip(RoundedCornerShape(checkBoxRadiusBorder))
            .then(
                if (checked) Modifier.background(gradientBrushForMainButton)
                else Modifier.background(cardColor)
            )
            .border(
                checkBoxRadiusBorderSize,
                borderColor,
                RoundedCornerShape(checkBoxRadiusBorder)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(sizeOfCheckIcon)
                .graphicsLayer { alpha = checkAlpha }
        )
    }
}