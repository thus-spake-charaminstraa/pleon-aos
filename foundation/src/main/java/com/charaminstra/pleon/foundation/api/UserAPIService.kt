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

    //    @GET("user")
//    suspend fun getUserData(
//        @Header("Authorization") verifyToken:String
//    ): Response<UserDataResponse>
}
