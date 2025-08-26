package com.example.notesapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.notesapp.models.User
import java.util.UUID

object UserHelper {
    private const val PREFS_NAME = "usersData"
    private lateinit var prefs: SharedPreferences


    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var user: User?
        get() {
            val username = prefs.getString("username", null)
            val email = prefs.getString("email", null)
            val password = prefs.getString("password", null)
            return if (username != null && email != null && password != null) {
                User(
                    Name = username,
                    Email = email,
                    Password = password
                )
            } else {
                null
            }
        }
        set(value) {
            prefs.edit().apply {
                if (value != null) {
                    putString("username", value.Name)
                    putString("email", value.Email)
                    putString("password", value.Password)
                } else {
                    remove("username")
                    remove("email")
                    remove("password")
                }
                apply()
            }
        }
}


//object SettingsHelper {
//    private const val PREFS_NAME = "settings"
//    private lateinit var prefs: SharedPreferences
//
//    fun init(context: Context) {
//        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//
//        val savedId = prefs.getString("idMy", null)
//        if (savedId != null) {
//            idMy = savedId
//        } else {
//            val newId = UUID.randomUUID().toString()
//            idMy = newId
//        }
//    }
//
//    var url: String?
//        get() = prefs.getString("url", null)
//        set(value) = prefs.edit().putString("url", value).apply()
//
//    var idMy: String?
//        get() = prefs.getString("idMy", null)
//        set(value) = prefs.edit().putString("idMy", value).apply()
//}





