package com.charaminstra.pleon.login

import com.charaminstra.pleon.foundation.APIInterface
import com.charaminstra.pleon.login.SplashActivity.Companion.prefs

import javax.inject.Inject

class AuthRepository @Inject constructor(private val service: APIInterface)  {
    suspend fun getAuth() = service.getAuth(accessToken = prefs.getAccessToken())
}