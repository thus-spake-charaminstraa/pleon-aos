package com.charaminstra.pleon.login

import com.charaminstra.pleon.foundation.AuthAPIService
import com.charaminstra.pleon.foundation.PleonPreference

import javax.inject.Inject

class AuthRepository @Inject constructor(private val service: AuthAPIService, private val prefs: PleonPreference)  {
    suspend fun getAuth() = service.getAuth(prefs.getAccessToken())
    suspend fun getRefresh() = service.getAuth(prefs.getRefreshToken())
}