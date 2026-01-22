package com.example.interviewaicoach.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.theme.appBarIconColor
import com.example.interviewaicoach.presentation.theme.directionOfInterviewFontSize
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.primaryTextColor
import com.example.interviewaicoach.presentation.theme.sizeOfIcons
import com.example.interviewaicoach.presentation.theme.sizeOfIconsButton


@Preview
@Composable
fun ChooseDirectionNavBar(
    modifier: Modifier = Modifier,
    onSettingsIconClicked: () -> Unit = {},
    onFavouriteIconClicked: () -> Unit = {},
    text: String = stringResource(R.string.begin_interview)
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            fontFamily = mainAppFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = primaryTextColor,
            fontSize = directionOfInterviewFontSize
        )
        IconButton(
            onClick = onFavouriteIconClicked,
            modifier = Modifier
                .size(sizeOfIconsButton)
                .clip(CircleShape)
        ) {
            Icon(
                modifier = Modifier.size(sizeOfIcons),
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = stringResource(R.string.saved_questions),
                tint = appBarIconColor
            )
        }
        Spacer(modifier = Modifier.width(20.dp))

        IconButton(
            onClick = onSettingsIconClicked,
            modifier = Modifier
                .size(sizeOfIconsButton)
                .clip(CircleShape)
        ) {
            Icon(
                modifier = Modifier
                    .size(sizeOfIcons),
                imageVector = Icons.Outlined.Settings,
                contentDescription = stringResource(R.string.settings),
                tint = appBarIconColor
            )
        }

    }
}