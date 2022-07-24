package com.charaminstra.pleon.plant_register

import com.charaminstra.pleon.foundation.PlantAPIService
import com.charaminstra.pleon.foundation.PleonPreference
import com.charaminstra.pleon.foundation.model.PlantRegisterRequestBody
import javax.inject.Inject

class PlantRegisterRepository @Inject constructor(private val service: PlantAPIService, private val prefs: PleonPreference) {
    suspend fun postPlant(name: String, species: String,water_date:String, adopt_date: String, light: String, air: String)
        = service.postPlant(
            prefs.getAccessToken(),
            PlantRegisterRequestBody(name, species, adopt_date,water_date,"", light, air)
        )
}