package com.charaminstra.pleon.common.api

import com.charaminstra.pleon.common.model.PlantDoctorRequestBody
import com.charaminstra.pleon.common.model.PlantDoctorResponseBody
import retrofit2.Response
import retrofit2.http.*

interface InferenceAPIService {
    //    plant detection
    @POST("inference/plant-detection/warming")
    suspend fun warmingPlantDetection(
        @Body warming: String
    ) : Response<Void>

    @POST("inference/plant-detection")
    suspend fun postPlantDetection(
        @Body plantDetectionRequestBody: com.charaminstra.pleon.common.model.PlantDetectionRequestBody
    ) : Response<com.charaminstra.pleon.common.model.PlantDetectionResponseBody>

    //    plant doctor
    @POST("inference/plant-doctor/warming")
    suspend fun warmingPlantDoctor(
        @Body warming: String
    ) : Response<Void>

    @POST("inference/plant-doctor")
    suspend fun postPlantDoctor(
        @Body plantDoctorRequestBody: PlantDoctorRequestBody
    ) : Response<PlantDoctorResponseBody>
}