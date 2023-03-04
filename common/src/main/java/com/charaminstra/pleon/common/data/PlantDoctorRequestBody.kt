package com.charaminstra.pleon.common.data

import java.util.*

data class PlantDoctorRequestBody(
    val image_urls: List<String>?,
    val plant_id: String?
)

data class PlantDoctorResponseBody(
    val success: Boolean,
    val statusCode: Int?,
    val data: PlantDoctorData?
)

data class PlantDoctorData(
    val symptoms: List<SymptomObject>,
    val causes: List<CauseObject>,
    val plant: PlantDataObject,
    val created_at: Date?,
)

data class SymptomObject(
    val symptom: String,
    val symptom_en: String,
    val symptom_ko: String,
    val image_url: String
)

data class CauseObject(
    val cause: String,
    val cause_ko: String,
    val cause_en: String,
    val guide: String
)
