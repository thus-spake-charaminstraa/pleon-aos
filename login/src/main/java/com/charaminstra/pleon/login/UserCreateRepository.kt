package com.charaminstra.pleon.login

import android.util.Log
import com.charaminstra.pleon.foundation.APIInterface
import com.charaminstra.pleon.foundation.PleonPreference
import com.charaminstra.pleon.foundation.model.UserCreateRequestBody
import com.charaminstra.pleon.foundation.model.UserCreateResponse
import retrofit2.Response
import javax.inject.Inject

class UserCreateRepository @Inject constructor(private val service: APIInterface){
    suspend fun postNickname(name: String, verify_token: String): Response<UserCreateResponse>{
        return service.postName(verify_token, UserCreateRequestBody(name)
        )
    }
}