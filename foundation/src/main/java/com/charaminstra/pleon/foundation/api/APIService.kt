package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface APIService{
    /* create user */
    @POST("user")
    suspend fun postName(
        @Header("Authorization") verifyToken:String,
        @Body userCreateRequestBody: UserCreateRequestBody
    ): Response<UserCreateResponse>
}
