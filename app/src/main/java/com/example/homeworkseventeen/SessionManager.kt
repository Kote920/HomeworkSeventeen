package com.example.homeworkseventeen

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_EMAIL = "email"
        private const val KEY_TOKEN = "token"
        private const val REMEMBER = "remember"
    }
    fun clearSession() {
        isLoggedIn = false
        editor.remove(KEY_EMAIL)
        editor.remove(KEY_TOKEN)
        remember = false
        editor.apply()
    }

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = editor.putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var userEmail: String?
        get() = sharedPreferences.getString(KEY_EMAIL, null)
        set(value) = editor.putString(KEY_EMAIL, value).apply()

    var remember: Boolean
        get() = sharedPreferences.getBoolean(REMEMBER, false)
        set(value) = editor.putBoolean(REMEMBER, value).apply()

    var userToken: String?
        get() = sharedPreferences.getString(KEY_TOKEN, null)
        set(value) = editor.putString(KEY_TOKEN, value).apply()
}