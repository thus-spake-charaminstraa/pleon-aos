package com.charaminstra.pleon.login

import com.charaminstra.pleon.foundation.APIInterface
import com.charaminstra.pleon.foundation.PleonPreference

import javax.inject.Inject

class AuthRepository @Inject constructor(private val service: APIInterface, private val prefs: PleonPreference)  {
    suspend fun getAuth() = service.getAuth(accessToken = prefs.getAccessToken())
}