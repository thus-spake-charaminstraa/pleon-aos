package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.PlantDetectionRequestBody
import com.charaminstra.pleon.foundation.model.PlantDetectionResponseBody
import retrofit2.Response
import retrofit2.http.*

interface InferenceAPIService {
    @POST("inference/plant-detection/warming")
    suspend fun warmingPlantDetection(
        @Body warming: String
    ) : Response<Void>

    @POST("inference/plant-detection")
    suspend fun postPlantDetection(
        @Body plantDetectionRequestBody: PlantDetectionRequestBody
    ) : Response<PlantDetectionResponseBody>
}