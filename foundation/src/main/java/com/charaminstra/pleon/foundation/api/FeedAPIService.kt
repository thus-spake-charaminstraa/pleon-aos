package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface FeedAPIService{
    /* post feed */
    @POST("feed")
    suspend fun postFeed(
        @Header("Authorization") verifyToken:String,
        @Body feedRequestBody: FeedRequestBody
    ): Response<FeedResponse>
}
