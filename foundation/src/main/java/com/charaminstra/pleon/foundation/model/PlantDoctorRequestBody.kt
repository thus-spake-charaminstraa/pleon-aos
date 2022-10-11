package com.charaminstra.pleon.foundation.model

data class PlantDoctorRequestBody(
    val image_urls: List<String>?
)

data class PlantDoctorResponseBody(
    val success: Boolean,
    val statusCode: Int?,
    val data: PlantDoctorData?
)

data class PlantDoctorData(
    val symptoms: List<SymptomObject>,
    val causes: List<CauseObject>
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
