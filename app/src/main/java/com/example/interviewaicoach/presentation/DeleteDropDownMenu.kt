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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.theme.answersFontSize
import com.example.interviewaicoach.presentation.theme.cardColor
import com.example.interviewaicoach.presentation.theme.deleteAllFontColor
import com.example.interviewaicoach.presentation.theme.deleteDropDownMenuRadiusClip
import com.example.interviewaicoach.presentation.theme.dropDownDeleteItemPadding
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.primaryTextColor
import com.example.interviewaicoach.presentation.theme.sizeOfIcons

@Preview
@Composable
fun DeleteDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    shape: Shape = RoundedCornerShape(deleteDropDownMenuRadiusClip),
    onFirstItemChosen: () -> Unit = {},
    firstItemText: String = stringResource(R.string.choose),
    secondItemText: String = stringResource(R.string.delete_all),
    secondElementsColor: Color = deleteAllFontColor,
    onSecondItemChosen: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {

    DropdownMenu(
        modifier = modifier
            .background(cardColor),
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        shape = shape
    ) {

        DropdownMenuItem(
            text = {
                Text(
                    text = firstItemText,
                    fontFamily = mainAppFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = primaryTextColor,
                    fontSize = answersFontSize
                )
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(sizeOfIcons),
                    imageVector = Icons.Outlined.CheckBox,
                    contentDescription = firstItemText,
                    tint = primaryTextColor
                )
            },

            onClick = onFirstItemChosen,
            contentPadding = PaddingValues(dropDownDeleteItemPadding)
        )

        DropdownMenuItem(
            text = {
                Text(
                    text = secondItemText,
                    fontFamily = mainAppFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = secondElementsColor,
                    fontSize = answersFontSize
                )
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(sizeOfIcons),
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = secondItemText,
                    tint = secondElementsColor
                )
            },

            onClick = onSecondItemChosen,
            contentPadding = PaddingValues(dropDownDeleteItemPadding)
        )

    }
}