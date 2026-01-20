package com.example.interviewaicoach.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.theme.darkThemeTextColor
import com.example.interviewaicoach.presentation.theme.lightIconColor
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.sizeOfIcons
import com.example.interviewaicoach.presentation.theme.sizeOfIconsButton
import com.example.interviewaicoach.presentation.theme.topControlPanelQuestionsScreenFontSize

@Preview
@Composable
fun QuestionsNavBar(
    modifier: Modifier = Modifier,
    onLeftIconClicked: () -> Unit = {},
    leftIcon: ImageVector = Icons.Outlined.Close,
    leftIconContentDescription: String = stringResource(R.string.exit_from_interview),
    text: String = stringResource(R.string.frontend),
    shouldBeRightIconButton: Boolean = false,
    onRightIconClicked: () -> Unit = {},
    rightIcon: ImageVector = Icons.Outlined.MoreVert,
    rightIconContentDescription: String = stringResource(R.string.delete_quest_from_fav),


    ) {

    Box(
        modifier = modifier.fillMaxWidth(),

        ) {
        IconButton(
            onClick = onLeftIconClicked,
            modifier = Modifier
                .size(sizeOfIconsButton)
                .clip(CircleShape)
        ) {
            Icon(
                modifier = Modifier
                    .size(sizeOfIcons),
                imageVector = leftIcon,
                contentDescription = leftIconContentDescription,
                tint = lightIconColor
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            fontFamily = mainAppFontFamily,
            fontWeight = FontWeight.Bold,
            color = darkThemeTextColor,
            fontSize = topControlPanelQuestionsScreenFontSize
        )
        if (shouldBeRightIconButton) {
            IconButton(
                onClick = onRightIconClicked,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(sizeOfIconsButton)
                    .clip(CircleShape)
            ) {
                Icon(
                    modifier = Modifier
                        .size(sizeOfIcons),
                    imageVector = rightIcon,
                    contentDescription = rightIconContentDescription,
                    tint = lightIconColor
                )
            }
        }

    }
}