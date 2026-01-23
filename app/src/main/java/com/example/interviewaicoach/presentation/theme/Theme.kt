package com.example.interviewaicoach.presentation.theme

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@SuppressLint("RememberReturnType")
@Composable
fun InterviewAiCoachTheme(
    content: @Composable () -> Unit
) {

    val context = LocalContext.current

    remember { getCurrentTheme(context) }

    val colorScheme by remember(
        currentTheme.collectAsStateWithLifecycle().value.isSystemDark,
    ) {
        mutableStateOf(
            darkColorScheme()
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = myBackGround.toArgb()// цвет АппБара
            window.navigationBarColor = myBackGround.toArgb() // цвет контрль панели
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                !currentTheme.value.isSystemDark // светлый или темный цвет для АппБара
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                !currentTheme.value.isSystemDark // для контроль панели
        }
    }
}
