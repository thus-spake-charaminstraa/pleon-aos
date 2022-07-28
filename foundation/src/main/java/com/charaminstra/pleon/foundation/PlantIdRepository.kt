package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.api.PlantAPIService
import com.charaminstra.pleon.foundation.api.PleonPreference
import javax.inject.Inject

class PlantIdRepository @Inject constructor(private val service: PlantAPIService, private val prefs: PleonPreference) {

    suspend fun getPlantId(id: String) = service.getPlantId(prefs.getAccessToken(),id)

    suspend fun patchPlantId(id: String) = service.patchPlantId(prefs.getAccessToken(),id)

    suspend fun deletePlantId(id: String) = service.deletePlantId(prefs.getAccessToken(),id)
}
