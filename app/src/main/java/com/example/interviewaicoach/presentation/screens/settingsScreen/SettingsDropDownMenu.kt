package com.example.interviewaicoach.presentation.screens.settingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.interviewaicoach.presentation.utils.LanguageType
import com.example.interviewaicoach.presentation.theme.answersFontSize
import com.example.interviewaicoach.presentation.theme.cardColor
import com.example.interviewaicoach.presentation.theme.deleteDropDownMenuRadiusClip
import com.example.interviewaicoach.presentation.theme.doneIconPadding
import com.example.interviewaicoach.presentation.theme.dropDownSettingsItemHorizontalPadding
import com.example.interviewaicoach.presentation.theme.dropDownSettingsItemVerticalPadding
import com.example.interviewaicoach.presentation.theme.gradientBrushForMainButton
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.primaryTextColor
import com.example.interviewaicoach.presentation.theme.sizeOfSettingsDoneIcon

@Preview
@Composable
fun SettingsDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    shape: Shape = RoundedCornerShape(deleteDropDownMenuRadiusClip),
    onItemClicked: (Int) -> Unit = {},
    currentLangId: Int = 0,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    onDismissRequest: () -> Unit = {}
) {

    val langTypesIds = LanguageType.entries.map { it.textResources }

    DropdownMenu(
        modifier = modifier
            .width(180.dp)
            .background(cardColor),
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        shape = shape,
        offset = offset,
    ) {


        langTypesIds.forEach {
            DropdownMenuItem(
                text = {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(it),
                            fontFamily = mainAppFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = primaryTextColor,
                            fontSize = answersFontSize
                        )
                        if (currentLangId == it) {
                            Icon(
                                modifier = Modifier
                                    .size(sizeOfSettingsDoneIcon)
                                    .clip(CircleShape)
                                    .background(brush = gradientBrushForMainButton)
                                    .padding(doneIconPadding),
                                imageVector = Icons.Rounded.Done,
                                contentDescription = stringResource(it),
                                tint = primaryTextColor
                            )
                        }
                    }
                },
                onClick = { onItemClicked(it) },
                contentPadding = PaddingValues(
                    vertical = dropDownSettingsItemVerticalPadding,
                    horizontal = dropDownSettingsItemHorizontalPadding
                )
            )
        }
    }
}