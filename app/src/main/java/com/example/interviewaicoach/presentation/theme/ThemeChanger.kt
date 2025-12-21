package com.example.interviewaicoach.presentation.theme

import android.content.Context
import com.example.interviewaicoach.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


enum class ThemeType(val textResources: Int) {
    LIGHT(textResources = R.string.white_option),
    DARK(textResources = R.string.black_option),
    SYSTEM(textResources = R.string.system_option)
}

data class ThemeState(
    val themeType: ThemeType = ThemeType.LIGHT,
    val isSystemDark: Boolean = false
)


private val _currentTheme = MutableStateFlow(ThemeState())
val currentTheme = _currentTheme.asStateFlow()

private fun updateCurrentThemeMode(
    themeType: ThemeType,
    isDarkTheme: Boolean
) {
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

        ThemeType.SYSTEM -> {
            _currentTheme.update {
                it.copy(
                    themeType = ThemeType.SYSTEM,
                    isSystemDark = isDarkTheme
                )
            }
        }
    }
}

fun saveCurrentTheme(
    context: Context,
    themeType: ThemeType,
    isDarkTheme: Boolean
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
    updateCurrentThemeMode(themeType, isDarkTheme)
}


fun getCurrentTheme(
    context: Context,
    isDarkTheme: Boolean
) {
    val sharedPref =
        context.applicationContext.getSharedPreferences(
            THEME_TYPE_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )

    val themeType = sharedPref.getString(THEME_TYPE_KEY, null)?.let { themeTypeName ->
        ThemeType.entries.find { it.name == themeTypeName }
    } ?: ThemeType.DARK

    updateCurrentThemeMode(themeType = themeType, isDarkTheme)

}

private const val THEME_TYPE_SHARED_PREFERENCES = "THEME_TYPE_SHARED_PREFERENCES"
private const val THEME_TYPE_KEY = "THEME_TYPE_KEY"