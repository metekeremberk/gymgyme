package com.example.gymgyme.utility

import android.content.Context

object SharedPreferencesManager {
    private const val PREF_NAME = "MyAppPreferences"
    private const val JWT_KEY = "jwt"

    fun saveJWT(context: Context, jwt: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(JWT_KEY, jwt).apply()
    }

    fun getJWT(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(JWT_KEY, "") ?: ""
    }
}
