package com.example.interviewaicoach.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.theme.answersFontSize
import com.example.interviewaicoach.presentation.theme.darkThemeTextColor
import com.example.interviewaicoach.presentation.theme.favCategoryArrowColor
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.sizeOfIcons
import com.example.interviewaicoach.presentation.theme.sizeOfIconsButton

@Preview
@Composable
fun FavouriteQuestionCard(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.frontend),
    shouldBeArrowInTheEnd: Boolean = true,
    isExpanded: Boolean = false,
    shouldBeArrowClicked: Boolean = false,
    onArrowClick: () -> Unit = {},
    textFontColor: Color = darkThemeTextColor
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = text,
                fontFamily = mainAppFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = textFontColor,
                fontSize = answersFontSize
            )
            if (shouldBeArrowInTheEnd) {

                if (shouldBeArrowClicked) {
                    IconButton(
                        onClick = onArrowClick,
                        modifier = Modifier
                            .size(sizeOfIconsButton)
                            .clip(CircleShape)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(sizeOfIcons),
                            imageVector = if (isExpanded) Icons.Filled.KeyboardArrowDown else Icons.Outlined.ChevronRight,
                            contentDescription = stringResource(R.string.go_into_category),
                            tint = favCategoryArrowColor
                        )
                    }
                } else {
                    Icon(
                        modifier = Modifier
                            .size(sizeOfIcons),
                        imageVector = if (isExpanded) Icons.Filled.KeyboardArrowDown else Icons.Outlined.ChevronRight,
                        contentDescription = stringResource(R.string.go_into_category),
                        tint = favCategoryArrowColor
                    )
                }
            }

        }
    }

}