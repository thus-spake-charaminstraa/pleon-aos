package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.api.InferenceAPIService
import com.charaminstra.pleon.foundation.model.PlantDetectionRequestBody
import javax.inject.Inject

class InferenceRepository @Inject constructor(private val service: InferenceAPIService)  {
    suspend fun warmingPlantDetection() = service.warmingPlantDetection(
        "warming"
    )

    suspend fun postPlantDetection(image_url: String) = service.postPlantDetection(
        PlantDetectionRequestBody(image_url)
    )
}