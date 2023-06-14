package com.charaminstra.pleon.common.repository

import com.charaminstra.pleon.common.api.PlantAPIService
import com.charaminstra.pleon.common.api.PleonPreference
import com.charaminstra.pleon.common.model.PlantEditRequestBody
import com.charaminstra.pleon.common.model.PlantRegisterRequestBody
import com.charaminstra.pleon.common.model.PlantResponse
import retrofit2.Response
import javax.inject.Inject

class PlantIdRepository @Inject constructor(private val service: PlantAPIService, private val prefs: PleonPreference) {

    suspend fun postPlant(name: String, species: String, water_date:String, adopt_date: String, thumbnail: String, light: String, air: String): Response<PlantResponse> {
        return service.postPlant(
            prefs.getAccessToken(),
            PlantRegisterRequestBody(name, species, adopt_date, water_date, thumbnail, light, air)
        )
    }

    suspend fun getPlantId(id: String) = service.getPlantId(prefs.getAccessToken(),id)

    suspend fun patchPlantId(id: String,
                             name : String,
                             adopt_date: String,
                             thumbnail: String,
                             light: String,
                             air: String)
    = service.patchPlantId(prefs.getAccessToken(), id,
        PlantEditRequestBody(name,adopt_date,thumbnail,light,air)
    )

    suspend fun deletePlantId(id: String) = service.deletePlantId(prefs.getAccessToken(),id)

    suspend fun getPlantDiagnosis(id: String) = service.getPlantDiagnosis(prefs.getAccessToken(),id)
}
