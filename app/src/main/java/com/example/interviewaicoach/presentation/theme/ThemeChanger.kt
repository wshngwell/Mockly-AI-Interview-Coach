package com.example.interviewaicoach.presentation.theme

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


enum class ThemeType {
    LIGHT,
    DARK,

}

data class ThemeState(
    val themeType: ThemeType = ThemeType.LIGHT,
    val isSystemDark: Boolean = false
)


private val _currentTheme = MutableStateFlow(ThemeState())
val currentTheme = _currentTheme.asStateFlow()

private fun updateCurrentThemeMode(themeType: ThemeType) {
    when (themeType) {
        ThemeType.LIGHT -> {
            _currentTheme.update {
                it.copy(
                    themeType = ThemeType.LIGHT,
                    isSystemDark = false
                )
            }
        }

        ThemeType.DARK -> {
            _currentTheme.update {
                it.copy(
                    themeType = ThemeType.DARK,
                    isSystemDark = true
                )
            }
        }
    }
}

fun saveCurrentTheme(
    context: Context,
    themeType: ThemeType,
) {
    val sharedPref =
        context.applicationContext.getSharedPreferences(
            THEME_TYPE_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
    with(sharedPref.edit()) {
        putString(THEME_TYPE_KEY, themeType.name)
        apply()
    }
    updateCurrentThemeMode(themeType)
}


fun getCurrentTheme(
    context: Context,
) {
    val sharedPref =
        context.applicationContext.getSharedPreferences(
            THEME_TYPE_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )

    val themeType = sharedPref.getString(THEME_TYPE_KEY, null)?.let { themeTypeName ->
        ThemeType.entries.find { it.name == themeTypeName }
    } ?: ThemeType.LIGHT

    updateCurrentThemeMode(themeType = themeType)

}

private const val THEME_TYPE_SHARED_PREFERENCES = "THEME_TYPE_SHARED_PREFERENCES"
private const val THEME_TYPE_KEY = "THEME_TYPE_KEY"