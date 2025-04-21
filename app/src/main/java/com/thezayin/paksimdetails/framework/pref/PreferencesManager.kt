package com.thezayin.paksimdetails.framework.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.thezayin.paksimdetails.ui.language.domain.model.Language
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PreferencesManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "com.thezayin.app.prefs"
        private const val KEY_IS_FIRST_TIME = "is_first_time"
        private const val KEY_SELECTED_LANGUAGE = "selected_language"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _isFirstTime = MutableStateFlow(true)
    val isFirstTime: StateFlow<Boolean> = _isFirstTime

    init {
        _isFirstTime.value = sharedPreferences.getBoolean(KEY_IS_FIRST_TIME, true)
    }

    /**
     * Marks that the user has completed onboarding.
     */
    fun setOnboardingCompleted() {
        sharedPreferences.edit {
            putBoolean(KEY_IS_FIRST_TIME, false)
        }
        _isFirstTime.value = false
    }

    /** Persist the selected language. */
    fun setSelectedLanguage(language: Language) {
        sharedPreferences.edit {
            putString(KEY_SELECTED_LANGUAGE, language.name)
        }
    }

    /** Retrieve the selected language, if available. */
    fun getSelectedLanguage(): Language? {
        return sharedPreferences.getString(KEY_SELECTED_LANGUAGE, null)?.let { languageName ->
            try {
                Language.valueOf(languageName)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}
