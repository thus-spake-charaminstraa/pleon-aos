package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.*
import retrofit2.Response
import retrofit2.http.*

interface NotiAPIService{

    @GET("noti/feed")
    suspend fun getNoti(
        @Header("Authorization") accessToken:String,
    ): Response<GuideListResponse>

//    @POST("noti/{id}")
//    suspend fun postNotiAction(
//        @Header("Authorization") accessToken:String,
//        @Path("id") id:String,
//        @Body noti: NotiRequestBody
//    ): Response<NotiResponse>

    @POST("plant/guide/manage")
    suspend fun postNotiAction(
        @Header("Authorization") accessToken:String,
        @Body noti: GuideRequestBody
    ): Response<GuideResponse>
}
