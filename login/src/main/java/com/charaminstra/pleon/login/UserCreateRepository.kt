package com.charaminstra.pleon.login

import android.util.Log
import com.charaminstra.pleon.foundation.APIInterface
import com.charaminstra.pleon.foundation.model.UserCreateRequestBody
import com.charaminstra.pleon.foundation.model.UserCreateResponse
import com.charaminstra.pleon.login.SplashActivity.Companion.prefs
import retrofit2.Response
import javax.inject.Inject

class UserCreateRepository @Inject constructor(private val service: APIInterface){
    suspend fun postNickname(name: String): Response<UserCreateResponse>{
        Log.d("UserCreateRepository", "token check"+prefs.getVerifyToken())
        return service.postName(
            verifyToken = prefs.getVerifyToken(),
            UserCreateRequestBody(name)
        )
    }
}

//eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIrODIxMDY2MzI5ODcyIiwiaWF0IjoxNjU3NzAzNzAxLCJleHAiOjE2NTc3MDU1MDF9.IhtRA009y7S_Jt6wzhF7A5-X4rI6EI57NhLV8OfupR4