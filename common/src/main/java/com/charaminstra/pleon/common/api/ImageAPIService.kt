package com.charaminstra.pleon.common.api

import com.charaminstra.pleon.foundation.model.ImageUrlResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageAPIService {
    @Multipart
    @POST("image/upload")
    suspend fun postImage(
        @Part image: MultipartBody.Part
    ) : Response<ImageUrlResponse>
}