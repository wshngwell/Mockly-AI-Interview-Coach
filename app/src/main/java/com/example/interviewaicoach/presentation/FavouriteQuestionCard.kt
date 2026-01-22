package com.example.interviewaicoach.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.theme.answersFontSize
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.primaryTextColor
import com.example.interviewaicoach.presentation.theme.secondaryTextColor
import com.example.interviewaicoach.presentation.theme.sizeOfIcons

@Preview
@Composable
fun FavouriteQuestionCard(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.frontend),
    shouldBeArrowInTheEnd: Boolean = true,
    isExpanded: Boolean = false,
    isDeletingMode: Boolean = false,
    checked: Boolean = false,
    textFontColor: Color = primaryTextColor
) {
    Box(
        modifier = modifier

    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isDeletingMode) {

                DeleteCheckbox(checked = checked)

                Spacer(modifier = Modifier.width(22.dp))
            }

            Text(
                text = text,
                fontFamily = mainAppFontFamily,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = textFontColor,
                fontSize = answersFontSize
            )
            if (shouldBeArrowInTheEnd) {
                Spacer(
                    modifier = Modifier.weight(
                        1f
                    )
                )
                Icon(
                    modifier = Modifier
                        .size(sizeOfIcons),
                    imageVector = if (isExpanded) Icons.Filled.KeyboardArrowDown else Icons.Outlined.ChevronRight,
                    contentDescription = stringResource(R.string.go_into_category),
                    tint = secondaryTextColor
                )
            }

        }
    }
}