package com.charaminstra.pleon.login

import com.charaminstra.pleon.foundation.APIService
import com.charaminstra.pleon.foundation.model.UserCreateRequestBody
import com.charaminstra.pleon.foundation.model.UserCreateResponse
import retrofit2.Response
import javax.inject.Inject

class UserCreateRepository @Inject constructor(private val service: APIService){
    suspend fun postNickname(name: String, verify_token: String): Response<UserCreateResponse>{
        return service.postName(verify_token, UserCreateRequestBody(name)
        )
    }
}