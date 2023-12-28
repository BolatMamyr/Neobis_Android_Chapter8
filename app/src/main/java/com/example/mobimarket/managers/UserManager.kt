package com.example.mobimarket.managers

import android.content.SharedPreferences
import javax.inject.Inject

class UserManager @Inject constructor(private val pref: SharedPreferences) {

    fun saveTokens(accessToken: String, refreshToken: String) {
        pref.edit()
            .putString(ACCESS_TOKEN_KEY, accessToken)
            .putString(REFRESH_TOKEN_KEY, refreshToken)
            .apply()
    }

    val accessToken: String
        get() = pref.getString(ACCESS_TOKEN_KEY, "") ?: ""

    val refreshToken: String
        get() = pref.getString(REFRESH_TOKEN_KEY, "") ?: ""


    companion object {
        const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
        const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
    }
}