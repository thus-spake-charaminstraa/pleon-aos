package com.charaminstra.pleon.login

import com.charaminstra.pleon.foundation.APIInterface
import com.charaminstra.pleon.foundation.model.UserCreateRequestBody
import com.charaminstra.pleon.foundation.model.UserCreateResponse
import com.charaminstra.pleon.login.SplashActivity.Companion.prefs
import retrofit2.Response
import javax.inject.Inject

class UserCreateRepository @Inject constructor(private val service: APIInterface){
    suspend fun postNickname(name: String): Response<UserCreateResponse>{
        return service.postName(
            verifyToken = prefs.getVerifyToken(),
            UserCreateRequestBody(name)
        )
    }
}