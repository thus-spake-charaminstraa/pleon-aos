package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.PlantEditRequestBody
import com.charaminstra.pleon.foundation.model.PlantRegisterRequestBody
import com.charaminstra.pleon.foundation.model.PlantResponse
import com.charaminstra.pleon.foundation.model.PlantsResponse
import retrofit2.Response
import retrofit2.http.*

interface PlantAPIService {
    @POST("plant")
    suspend fun postPlant(
        @Header("Authorization") accessToken:String,
        @Body plantRegisterRequestBody: PlantRegisterRequestBody
    ): Response<PlantResponse>

    @GET("plant")
    suspend fun getPlants(
        @Header("Authorization") accessToken:String
    ): Response<PlantsResponse>

    @GET("plant/{id}")
    suspend fun getPlantId(
        @Header("Authorization") accessToken:String,
        @Path("id") id: String
    ): Response<PlantResponse>

    @PATCH("plant/{id}")
    suspend fun patchPlantId(
        @Header("Authorization") accessToken:String,
        @Path("id") id: String,
        @Body plantEditRequestBody: PlantEditRequestBody
    ): Response<PlantResponse>

    @DELETE("plant/{id}")
    suspend fun deletePlantId(
        @Header("Authorization") accessToken:String,
        @Path("id") id: String
    ): Response<PlantResponse>
}