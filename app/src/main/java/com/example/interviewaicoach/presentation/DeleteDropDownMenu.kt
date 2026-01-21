package com.example.interviewaicoach.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.theme.answersFontSize
import com.example.interviewaicoach.presentation.theme.clipParamsForQuestionWithAnswerBox
import com.example.interviewaicoach.presentation.theme.darkThemeTextColor
import com.example.interviewaicoach.presentation.theme.deleteAllFontColor
import com.example.interviewaicoach.presentation.theme.dropDownItemPadding
import com.example.interviewaicoach.presentation.theme.dropDownMenuRadiusClip
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.questionWithAnswerCardColor
import com.example.interviewaicoach.presentation.theme.sizeOfIcons

@Preview
@Composable
fun DeleteDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onChooseClicked: () -> Unit = {},
    onDeleteAllClicked: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {

    DropdownMenu(
        modifier = modifier
            .background(questionWithAnswerCardColor),
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(dropDownMenuRadiusClip)
    ) {

        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.choose),
                    fontFamily = mainAppFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = darkThemeTextColor,
                    fontSize = answersFontSize
                )
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(sizeOfIcons),
                    imageVector = Icons.Outlined.CheckBox,
                    contentDescription = stringResource(R.string.choose),
                    tint = darkThemeTextColor
                )
            },
            onClick = onChooseClicked,
            contentPadding = PaddingValues(dropDownItemPadding)
        )

        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.delete_all),
                    fontFamily = mainAppFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = deleteAllFontColor,
                    fontSize = answersFontSize
                )
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(sizeOfIcons),
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = stringResource(R.string.choose),
                    tint = deleteAllFontColor
                )
            },
            onClick = onDeleteAllClicked,
            contentPadding = PaddingValues(dropDownItemPadding)
        )

    }
}