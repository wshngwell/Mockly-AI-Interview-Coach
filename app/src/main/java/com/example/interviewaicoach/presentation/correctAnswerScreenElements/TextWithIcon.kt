package com.example.interviewaicoach.presentation.correctAnswerScreenElements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.theme.BottomButtonFontSize
import com.example.interviewaicoach.presentation.theme.buttonsTextColor
import com.example.interviewaicoach.presentation.theme.lightIconColor
import com.example.interviewaicoach.presentation.theme.sizeOfIcons
import com.example.interviewaicoach.presentation.theme.widthBetweenIconAndTextInBottomButton

@Composable
fun TextWithIconButton(
    modifier: Modifier,
    imageVector: ImageVector = Icons.Outlined.BookmarkBorder,
    textOnButton: String = stringResource(R.string.save_question),
    colorOfIcon: Color = lightIconColor,
    textFontSize:Int = BottomButtonFontSize,
    textColor: Color = buttonsTextColor,
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = textOnButton,
                color = textColor,
                fontSize = textFontSize.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.width(widthBetweenIconAndTextInBottomButton))

            Icon(
                modifier = Modifier.size(sizeOfIcons),
                imageVector = imageVector,
                contentDescription = stringResource(R.string.exit_from_interview),
                tint = colorOfIcon
            )
        }
    }
}