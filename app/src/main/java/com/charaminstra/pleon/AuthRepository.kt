package com.charaminstra.pleon

import com.charaminstra.pleon.PleonApplication.Companion.prefs
import com.charaminstra.pleon.foundation.APIInterface
import com.charaminstra.pleon.foundation.model.SmsRequestBody
import com.charaminstra.pleon.login.ui.LoginActivity
import javax.inject.Inject

class AuthRepository @Inject constructor(private val service: APIInterface)  {
    suspend fun getAuth() = service.getAuth(accessToken = prefs.getAccessToken())

}