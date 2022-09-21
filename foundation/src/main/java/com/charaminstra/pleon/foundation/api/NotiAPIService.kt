package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.*
import retrofit2.Response
import retrofit2.http.*

interface NotiAPIService{
    @GET("noti")
    suspend fun getNoti(
        @Header("Authorization") accessToken:String,
    ): Response<NotiListResponse>

    @POST("noti/{id}")
    suspend fun postNotiAction(
        @Header("Authorization") accessToken:String,
        @Path("id") id:String,
        @Body noti: NotiRequestBody
    ): Response<NotiResponse>
}
