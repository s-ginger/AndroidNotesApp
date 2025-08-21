package com.example.notesapp.utils

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.notesapp.R
import androidx.core.content.edit

object ThemeHelper {
    private const val PREF_NAME = "app_theme"
    private const val KEY_THEME = "theme"

    fun applyTheme(activity: Activity) {
        when (getSavedTheme(activity)) {
            "dark" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                activity.setTheme(R.style.AppDarkTheme)
            }
            "light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                activity.setTheme(R.style.AppTheme)
            }
        }
    }



    fun saveTheme(context: Context, theme: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit {
                putString(KEY_THEME, theme)
            }
    }

    fun getSavedTheme(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_THEME, "light") ?: "light"
    }

}