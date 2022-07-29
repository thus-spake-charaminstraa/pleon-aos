package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.api.PlantAPIService
import com.charaminstra.pleon.foundation.api.PleonPreference
import javax.inject.Inject

class PlantsRepository @Inject constructor(private val service: PlantAPIService, private val prefs: PleonPreference)  {
    suspend fun getPlants() = service.getPlants(prefs.getAccessToken())
}