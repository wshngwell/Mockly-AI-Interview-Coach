package com.example.interviewaicoach.presentation

import android.content.Context
import android.content.res.Configuration
import com.example.interviewaicoach.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

enum class LanguageType(
    val textResources: Int,
    val locale: String
) {
    RUSSIAN(textResources = R.string.russian, locale = "ru"),
    ENGLISH(textResources = R.string.english, locale = "en")
}

private val _currentLang = MutableStateFlow(LanguageType.RUSSIAN)
val currentLang = _currentLang.asStateFlow()


private fun Context.applyLang(lang: String) {
    val locale = Locale(lang)
    Locale.setDefault(locale)
    val configuration = Configuration()
    configuration.locale = locale
    this.resources.updateConfiguration(configuration, null)
    applicationContext.resources.updateConfiguration(configuration, null)
}

fun saveLanguage(context: Context, languageType: LanguageType) {
    val sharedPref = context.applicationContext.getSharedPreferences(
        LANGUAGE_TYPE_SHARED_PREFERENCES,
        Context.MODE_PRIVATE
    )
    with(sharedPref.edit()) {
        putString(LANGUAGE_TYPE_KEY, languageType.locale)
        apply()
    }
    context.applyLang(languageType.locale)
    _currentLang.update { languageType }
}

fun Context.getLanguage() {
    val sharedPref = applicationContext.getSharedPreferences(
        LANGUAGE_TYPE_SHARED_PREFERENCES,
        Context.MODE_PRIVATE
    )
    val langTypeName = sharedPref.getString(LANGUAGE_TYPE_KEY, null)?.let { langTypeName ->
        LanguageType.entries.find { it.locale == langTypeName }
    } ?: LanguageType.RUSSIAN

    applyLang(langTypeName.locale)
    _currentLang.update { langTypeName }
}

private const val LANGUAGE_TYPE_SHARED_PREFERENCES = "LANGUAGE_TYPE_SHARED_PREFERENCES"
private const val LANGUAGE_TYPE_KEY = "LANGUAGE_TYPE_KEY"


