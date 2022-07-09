package com.charaminstra.pleon.foundation

import android.content.Context
import android.content.SharedPreferences

const val VERIFY_KEY = "verify"
const val ACCESS_KEY = "access"
const val REFRESH_KEY = "refresh"

class PreferenceUtil(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getVerifyToken(key: String, defValue: String): String {
        return prefs.getString(VERIFY_KEY, defValue).toString()
    }
    fun setVerifyToken(str: String) {
        prefs.edit().putString(VERIFY_KEY, str).apply()
    }

    fun getAccessToken(key: String, defValue: String): String {
        return prefs.getString(ACCESS_KEY, defValue).toString()
    }
    fun setAccessToken(str: String) {
        prefs.edit().putString(ACCESS_KEY, str).apply()
    }

    fun getRefreshToken(key: String, defValue: String): String {
        return prefs.getString(REFRESH_KEY, defValue).toString()
    }
    fun setRefreshToken(str: String) {
        prefs.edit().putString(REFRESH_KEY, str).apply()
    }

    fun delete(key: String) {
        prefs.edit().remove(key).apply()
    }
}