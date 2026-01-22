package com.example.interviewaicoach.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.correctAnswerScreenElements.IconWithText
import com.example.interviewaicoach.presentation.correctAnswerScreenElements.TextWithIcon
import com.example.interviewaicoach.presentation.theme.ThemeType
import com.example.interviewaicoach.presentation.theme.appBarIconColor
import com.example.interviewaicoach.presentation.theme.cardColor
import com.example.interviewaicoach.presentation.theme.clipParamsForQuestionWithAnswerBox
import com.example.interviewaicoach.presentation.theme.currentTheme
import com.example.interviewaicoach.presentation.theme.deleteIconWithTextHorizontalPadding
import com.example.interviewaicoach.presentation.theme.deleteIconWithTextPadding
import com.example.interviewaicoach.presentation.theme.horizontalDividerColor
import com.example.interviewaicoach.presentation.theme.mainAppFontFamily
import com.example.interviewaicoach.presentation.theme.primaryTextColor
import com.example.interviewaicoach.presentation.theme.resultCardInnerPadding
import com.example.interviewaicoach.presentation.theme.resultListItemsFontSize
import com.example.interviewaicoach.presentation.theme.saveCurrentTheme
import com.example.interviewaicoach.presentation.theme.secondaryTextColor
import com.example.interviewaicoach.presentation.theme.settingsDropDownMenuRadiusClip
import com.example.interviewaicoach.presentation.theme.settingsScreenHorizontalDividerVerticalPadding
import com.example.interviewaicoach.presentation.theme.sixteenSp
import com.example.interviewaicoach.presentation.theme.widthBetweenTextAndIcon

@Preview
@Composable
fun SettingsCard(
    modifier: Modifier = Modifier,
) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    var isChecked by rememberSaveable { mutableStateOf(currentTheme.value.isSystemDark) }

    val verticalScroll = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(verticalScroll)
            .fillMaxWidth()
            .background(
                color = cardColor,
                shape = RoundedCornerShape(clipParamsForQuestionWithAnswerBox)
            )
            .padding(resultCardInnerPadding)

    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.system),
            fontFamily = mainAppFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = secondaryTextColor,
            fontSize = sixteenSp,
            textAlign = TextAlign.Start
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = settingsScreenHorizontalDividerVerticalPadding),
            thickness = 1.dp,
            color = horizontalDividerColor
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconWithText(
                imageVector = Icons.Outlined.Translate,
                leftText = stringResource(R.string.language),
                colorOfIcon = secondaryTextColor,
                leftTextColor = primaryTextColor,
                sizeOfText = resultListItemsFontSize,
                fontWeight = FontWeight.Medium,
                contentAlignment = Alignment.TopStart
            )
            Column(
                horizontalAlignment = Alignment.End
            ) {

                val langState = currentLang.collectAsStateWithLifecycle().value

                val langTypesIds = LanguageType.entries.map { it.textResources }

                TextWithIcon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    expanded = expanded.not()
                                }
                            )
                        }
                        .padding(
                            horizontal = deleteIconWithTextHorizontalPadding,
                            vertical = deleteIconWithTextPadding
                        ),
                    textColor = primaryTextColor,
                    imageVector = if (expanded) Icons.Outlined.KeyboardArrowDown else Icons.Outlined.ChevronRight,
                    colorOfIcon = if (expanded) appBarIconColor else secondaryTextColor,
                    widthBetweenTextAndIcon = widthBetweenTextAndIcon,
                    textOnButton = stringResource(langState.textResources),
                    textFontSize = resultListItemsFontSize,
                )
                val context = LocalContext.current

                SettingsDropDownMenu(
                    expanded = expanded,
                    shape = RoundedCornerShape(settingsDropDownMenuRadiusClip),
                    langTypesIds = langTypesIds,
                    currentLangId = langState.textResources,
                    onItemClicked = { item ->
                        saveLanguage(
                            context = context,
                            languageType = LanguageType.entries.find { it.textResources == item }
                                ?: LanguageType.RUSSIAN
                        )
                        expanded = false
                    },
                    offset = DpOffset(x = 0.dp, y = 4.dp),
                    onDismissRequest = {
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconWithText(
                imageVector = Icons.Outlined.DarkMode,
                leftText = stringResource(R.string.dark_theme),
                colorOfIcon = secondaryTextColor,
                leftTextColor = primaryTextColor,
                sizeOfText = resultListItemsFontSize,
                fontWeight = FontWeight.Medium,
                contentAlignment = Alignment.TopStart
            )
            val context = LocalContext.current

            GradientSwitch(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = isChecked.not()
                    saveCurrentTheme(
                        context = context,
                        themeType = if (isChecked) ThemeType.DARK else ThemeType.LIGHT
                    )
                }
            )
        }

    }
}