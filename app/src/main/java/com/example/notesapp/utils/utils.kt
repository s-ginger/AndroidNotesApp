package com.example.notesapp.utils

import android.content.Intent
import android.os.Build
import android.os.Parcelable


@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Intent.safeGetParcelable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, T::class.java)
    } else {
        getParcelableExtra(key)
    }
}


