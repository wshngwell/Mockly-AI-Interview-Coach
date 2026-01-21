package com.example.interviewaicoach.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.correctAnswerScreenElements.IconWithText
import com.example.interviewaicoach.presentation.theme.bottomButtonSaveQuestionScreenVerticalPadding
import com.example.interviewaicoach.presentation.theme.darkThemeTextColor
import com.example.interviewaicoach.presentation.theme.deleteAllFontColor
import com.example.interviewaicoach.presentation.theme.deleteCountFontSize
import com.example.interviewaicoach.presentation.theme.deleteIconWithTextPadding
import com.example.interviewaicoach.presentation.theme.disabledColorForTextForMainButton
import com.example.interviewaicoach.presentation.theme.lightIconColor
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.pink
import com.example.interviewaicoach.presentation.theme.sizeOfIcons
import com.example.interviewaicoach.presentation.theme.sizeOfIconsButton
import com.example.interviewaicoach.presentation.theme.topControlPanelQuestionsScreenFontSize
import com.example.interviewaicoach.presentation.theme.transparentGradientBrush

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
    isExpandedDropDownMenu: Boolean = false,
    onDismissDropDownMenu: () -> Unit = {},
    isDeletingMode: Boolean = false,
    countOfSelectedQuestions: Int = 0,
    onChooseItemInDropDownMenuClicked: () -> Unit = {},
    onDeleteAllItemInDropDownMenuClicked: () -> Unit = {},
    onDeleteSelectedItems: () -> Unit = {},
) {

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        if (isDeletingMode && countOfSelectedQuestions > 0) {
            IconWithText(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .background(
                        brush = transparentGradientBrush
                    )
                    .clip(CircleShape)
                    .clickable {
                        onDeleteSelectedItems()
                    }
                    .padding(deleteIconWithTextPadding),
                imageVector = leftIcon,
                leftText = "$countOfSelectedQuestions",
                colorOfIcon = deleteAllFontColor,
                sizeOfText = deleteCountFontSize,
                textColor = deleteAllFontColor
            )
        } else {

            IconButton(
                onClick = onLeftIconClicked,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(sizeOfIconsButton)
                    .clip(CircleShape),
                enabled = !isDeletingMode
            ) {
                Icon(
                    modifier = Modifier
                        .size(sizeOfIcons),
                    imageVector = leftIcon,
                    contentDescription = leftIconContentDescription,
                    tint = if (isDeletingMode && countOfSelectedQuestions <= 0) disabledColorForTextForMainButton
                    else if (isDeletingMode) deleteAllFontColor
                    else lightIconColor
                )
            }
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            fontFamily = mainAppFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = darkThemeTextColor,
            fontSize = topControlPanelQuestionsScreenFontSize
        )
        if (shouldBeRightIconButton) {

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(sizeOfIconsButton)
                    .clip(CircleShape)
            ) {
                IconButton(onClick = onRightIconClicked) {
                    Icon(
                        modifier = Modifier
                            .size(sizeOfIcons),
                        imageVector = rightIcon,
                        contentDescription = rightIconContentDescription,
                        tint = lightIconColor
                    )
                }
                if (!isDeletingMode) {
                    DeleteDropDownMenu(
                        expanded = isExpandedDropDownMenu,
                        onChooseClicked = onChooseItemInDropDownMenuClicked,
                        onDismissRequest = onDismissDropDownMenu,
                        onDeleteAllClicked = onDeleteAllItemInDropDownMenuClicked
                    )
                }
            }
        }

    }
}