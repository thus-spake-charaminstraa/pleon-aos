package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.PlantRegisterRequestBody
import com.charaminstra.pleon.foundation.model.PlantRegisterResponse
import com.charaminstra.pleon.foundation.model.PlantsResponse
import retrofit2.Response
import retrofit2.http.*

interface PlantAPIService {
    @POST("plant")
    suspend fun postPlant(
        @Header("Authorization") accessToken:String,
        @Body plantRegisterRequestBody: PlantRegisterRequestBody
    ): Response<PlantRegisterResponse>

    @GET("plant")
    suspend fun getPlants(
        @Header("Authorization") accessToken:String
    ): Response<PlantsResponse>
}