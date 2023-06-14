package com.charaminstra.pleon.common.repository

import com.charaminstra.pleon.common.model.PlantDoctorRequestBody
import javax.inject.Inject

class InferenceRepository @Inject constructor(private val service: com.charaminstra.pleon.common.api.InferenceAPIService)  {
    suspend fun warmingPlantDetection() = service.warmingPlantDetection(
        "warming"
    )

    suspend fun postPlantDetection(image_url: String) = service.postPlantDetection(
        com.charaminstra.pleon.common.model.PlantDetectionRequestBody(image_url)
    )

    suspend fun warmingPlantDoctor() = service.warmingPlantDoctor(
        "warming"
    )

    suspend fun postPlantDoctor(image_url_first: String,image_url_second: String, plant_id:String?) = service.postPlantDoctor(
        PlantDoctorRequestBody(listOf(image_url_first,image_url_second), plant_id)
    )
}