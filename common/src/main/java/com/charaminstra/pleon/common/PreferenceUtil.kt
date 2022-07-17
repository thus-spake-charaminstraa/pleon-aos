package com.charaminstra.pleon.common

import android.content.Context
import android.content.SharedPreferences

const val VERIFY_KEY = "verify"
const val ACCESS_KEY = "access"
const val REFRESH_KEY = "refresh"
const val DEFAULT = "default"

class PreferenceUtil(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun setVerifyToken(str: String?) {
        prefs.edit().putString(VERIFY_KEY, str).apply()
    }
    fun getVerifyToken(): String {
        return "Bearer "+prefs.getString(VERIFY_KEY, DEFAULT).toString()
    }

    fun setAccessToken(str: String?) {
        prefs.edit().putString(ACCESS_KEY, str).apply()
    }
    fun getAccessToken(): String {
        return "Bearer "+prefs.getString(ACCESS_KEY, DEFAULT).toString()
    }

    fun setRefreshToken(str: String?) {
        prefs.edit().putString(REFRESH_KEY, str).apply()
    }
    fun getRefreshToken(): String {
        return prefs.getString(REFRESH_KEY, DEFAULT).toString()
    }

    fun delete() {
        prefs.edit().remove(VERIFY_KEY).apply()
        prefs.edit().remove(ACCESS_KEY).apply()
        prefs.edit().remove(REFRESH_KEY).apply()
    }
}