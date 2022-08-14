package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthAPIService {
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
        @Body smsRequestBody: SmsCodeRequestBody
    ): Response<SmsResponse>

    /* login */
    @POST("auth/login")
    suspend fun postLogin(
        @Header("Authorization") verifyToken:String,
    ): Response<LoginResponse>
}