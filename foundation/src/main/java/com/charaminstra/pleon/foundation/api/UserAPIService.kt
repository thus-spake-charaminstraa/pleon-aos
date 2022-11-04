package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.*
import retrofit2.Response
import retrofit2.http.*

interface UserAPIService{
    /* create user */
    @POST("user")
    suspend fun postName(
        @Header("Authorization") verifyToken:String,
        @Body userRequestBody: UserRequestBody
    ): Response<UserCreateResponse>

    @PATCH("user")
    suspend fun patchUserData(
        @Header("Authorization") accessToken:String,
        @Body userRequestBody: UserRequestBody
    ): Response<AuthResponse>

    @PATCH("user")
    suspend fun patchUserPushSetting(
        @Header("Authorization") accessToken:String,
        @Body userRequestBody: UserPushSettingRequestBody
    ): Response<AuthResponse>

    @POST("user/token")
    suspend fun postDeviceToken(
        @Header("Authorization") accessToken:String,
        @Body deviceTokenRequestBody:DeviceTokenRequestBody
    ): Response<AuthResponse>

}
