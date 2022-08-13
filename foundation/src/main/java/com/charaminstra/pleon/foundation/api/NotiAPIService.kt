package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface NotiAPIService{
    @POST("noti/{id}")
    suspend fun postNotiAction(
        @Header("Authorization") verifyToken:String,
        @Path("id") id:String,
        @Body noti: NotiRequestBody
    ): Response<NotiResponse>
}
