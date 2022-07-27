package com.charaminstra.pleon.plant_register

import android.util.Log
import com.charaminstra.pleon.foundation.api.PlantAPIService
import com.charaminstra.pleon.foundation.api.PleonPreference
import com.charaminstra.pleon.foundation.model.PlantRegisterRequestBody
import com.charaminstra.pleon.foundation.model.PlantRegisterResponse
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import java.io.File
import javax.inject.Inject

class PlantRegisterRepository @Inject constructor(private val service: PlantAPIService, private val prefs: PleonPreference) {
    suspend fun postPlant(name: String, species: String, water_date:String, adopt_date: String, thumbnail: String, light: String, air: String): Response<PlantRegisterResponse> {
        Log.i("plant register",
            PlantRegisterRequestBody(name, species, adopt_date, water_date, thumbnail, light, air).toString()
        )
        return service.postPlant(
            prefs.getAccessToken(),
            PlantRegisterRequestBody(name, species, adopt_date, water_date, thumbnail, light, air)
        )
    }
}
