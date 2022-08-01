package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FeedAPIService{
    /* post feed */
    @POST("feed")
    suspend fun postFeed(
        @Header("Authorization") verifyToken:String,
        @Body feedRequestBody: FeedRequestBody
    ): Response<FeedResponse>

    /*get feed*/
    @GET("feed")
    suspend fun getFeed(
        @Header("Authorization") verifyToken:String
    ): Response<FeedResponse>
}
