package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface APIInterface{
    @GET("auth/me")
    suspend fun getAuth(
        @Header("Authorization") accessToken:String,
    ): Response<AuthResponse>

    @POST("auth/send-sms")
    suspend fun postPhone(
        @Body phoneRequestBody: SmsRequestBody
    ): Response<SmsResponse>

    @POST("auth/verify-sms")
    suspend fun postCode(
        @Body smsRequestBody: SmsRequestBody
    ): Response<SmsResponse>

    /* create user */
    @POST("user")
    suspend fun postName(
        @Header("Authorization") verifyToken:String,
        @Body userCreateRequestBody: UserCreateRequestBody
    ): Response<UserCreateResponse>

    /* login */
    @POST("auth/login")
    suspend fun postLogin(
        @Header("Authorization") verifyToken:String,
    ): Response<TokenObject>
}
