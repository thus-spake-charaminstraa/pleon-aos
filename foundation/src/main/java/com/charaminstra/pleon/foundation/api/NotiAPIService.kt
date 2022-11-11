package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.*
import retrofit2.Response
import retrofit2.http.*

interface NotiAPIService{

    //guide in feed
    @GET("noti/feed")
    suspend fun getGuideList(
        @Header("Authorization") accessToken:String,
    ): Response<GuideListResponse>

    @POST("plant/guide/manage")
    suspend fun postNotiAction(
        @Header("Authorization") accessToken:String,
        @Body noti: GuideRequestBody
    ): Response<GuideResponse>

    //notification
    @GET("noti/list")
    suspend fun getNotiList(
        @Header("Authorization") accessToken:String,
    ): Response<NotiListResponse>

    //noti dialog in feed
    @GET("noti/feed-modal")
    suspend fun getNotiDialog(
        @Header("Authorization") accessToken:String,
    ): Response<NotiDialogResponse>
}
