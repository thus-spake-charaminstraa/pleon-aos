package com.charaminstra.pleon.common.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val VERIFY_KEY = "verify"
const val ACCESS_KEY = "access"
const val REFRESH_KEY = "refresh"
const val DEFAULT = "default"
const val NAME = "name"

const val DEVICE_KEY = "device"

class PleonPreference @Inject constructor(@ApplicationContext context : Context){

    private val prefs: SharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun setVerifyToken(str: String?) {
        prefs.edit().putString(VERIFY_KEY, str).apply()
        Log.d(VERIFY_KEY, "token set : "+ getVerifyToken() )
    }
    fun getVerifyToken(): String {
        return "Bearer "+prefs.getString(VERIFY_KEY, DEFAULT).toString()
    }

    fun setAccessToken(str: String?) {
        prefs.edit().putString(ACCESS_KEY, str).apply()
        Log.d(ACCESS_KEY, "token set : "+ getAccessToken() )
    }
    fun getAccessToken(): String {
        return "Bearer "+prefs.getString(ACCESS_KEY, DEFAULT).toString()
    }
    fun setRefreshToken(str: String?) {
        prefs.edit().putString(REFRESH_KEY, str).apply()
        Log.d(REFRESH_KEY, "token set : "+ getRefreshToken() )
    }
    fun getRefreshToken(): String {
        return "Bearer "+prefs.getString(REFRESH_KEY, DEFAULT).toString()
    }

    fun setDeviceToken(str: String?) {
        prefs.edit().putString(DEVICE_KEY, str).apply()
        Log.d(DEVICE_KEY, "token set : "+ getDeviceToken() )
    }
    fun getDeviceToken(): String {
        return prefs.getString(DEVICE_KEY, DEFAULT).toString()
    }

    fun delete() {
        prefs.edit().remove(VERIFY_KEY).apply()
        prefs.edit().remove(ACCESS_KEY).apply()
        prefs.edit().remove(REFRESH_KEY).apply()
    }
}
