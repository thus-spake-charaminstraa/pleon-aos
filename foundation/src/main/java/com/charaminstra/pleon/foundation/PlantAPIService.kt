package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.model.PlantRegisterRequestBody
import com.charaminstra.pleon.foundation.model.PlantRegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PlantAPIService {
    @POST("plant")
    suspend fun postPlant(
        @Header("Authorization") accessToken:String,
        @Body plantRegisterRequestBody: PlantRegisterRequestBody
    ): Response<PlantRegisterResponse>
}