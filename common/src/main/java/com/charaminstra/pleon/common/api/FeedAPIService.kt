package com.charaminstra.pleon.common.api

import com.charaminstra.pleon.common.model.FeedIdResponse
import com.charaminstra.pleon.common.FeedListResponse
import com.charaminstra.pleon.common.model.ActionResponse
import com.charaminstra.pleon.common.model.FeedRequestBody
import retrofit2.Response
import retrofit2.http.*

interface FeedAPIService{
    /* post feed */
    @POST("feed")
    suspend fun postFeed(
        @Header("Authorization") accessToken:String,
        @Body feedRequestBody: FeedRequestBody
    ): Response<FeedIdResponse>

    /*get feed*/
    @GET("feed/list")
    suspend fun getFeedList(
        @Header("Authorization") accessToken:String,
        @Query("limit") limit:Int?,
        @Query("offset") offset:Int?,
        @Query("plant_id")  plantId:String?,
        @Query("publish_date") date:String?
    ): Response<FeedListResponse>

    @GET("feed/{id}")
    suspend fun getFeedId(
        @Header("Authorization") accessToken:String,
        @Path("id") id: String
    ): Response<FeedIdResponse>

    @DELETE("feed/{id}")
    suspend fun deleteFeedId(
        @Header("Authorization") accessToken:String,
        @Path("id") id: String
    ): Response<FeedListResponse>

    @GET("feed/kind")
    suspend fun getAction(): Response<ActionResponse>
}
