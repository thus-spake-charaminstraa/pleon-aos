package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.*
import retrofit2.Response
import retrofit2.http.*

interface CommentAPIService {
    @POST("comment")
    suspend fun postComment(
        @Header("Authorization") accessToken: String,
        @Body commentRequestBody: CommentRequestBody
    ): Response<CommentPostResponse>

    @GET("comment/feed/{feed_id}")
    suspend fun getComment(
        @Header("Authorization") accessToken: String,
        @Path("feed_id") feedId: String
    ): Response<CommentResponse>
}