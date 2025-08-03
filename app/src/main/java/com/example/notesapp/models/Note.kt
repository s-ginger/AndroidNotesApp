package com.example.notesapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

sealed class Data


@Parcelize
data class Note(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val text: String,
    val createdAt: String = getCurrentDateTime()
) : Parcelable, Data()

fun getCurrentDateTime(): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return formatter.format(Date())
}


