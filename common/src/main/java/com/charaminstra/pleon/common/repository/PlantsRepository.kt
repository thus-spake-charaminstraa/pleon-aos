package com.charaminstra.pleon.common.repository

import com.charaminstra.pleon.common.api.PleonPreference
import javax.inject.Inject

class PlantsRepository @Inject constructor(private val service: com.charaminstra.pleon.common.api.PlantAPIService, private val prefs: PleonPreference)  {
    suspend fun getPlants() = service.getPlants(prefs.getAccessToken())

    suspend fun getPlantSpecies() = service.getPlantSpecies(prefs.getAccessToken())
}