package com.charaminstra.pleon.login

import com.charaminstra.pleon.foundation.api.UserAPIService
import com.charaminstra.pleon.foundation.api.PleonPreference
import com.charaminstra.pleon.foundation.model.UserCreateRequestBody
import com.charaminstra.pleon.foundation.model.UserCreateResponse
import retrofit2.Response
import javax.inject.Inject

class UserCreateRepository @Inject constructor(private val service: UserAPIService, private val prefs: PleonPreference){
    suspend fun postNickname(name: String): Response<UserCreateResponse>{
        return service.postName(prefs.getVerifyToken(), UserCreateRequestBody(name)
        )
    }
}