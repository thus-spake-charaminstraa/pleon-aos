package com.charaminstra.pleon.foundation.model

data class PlantsResponse(
    val success: Boolean,
    val statusCode: Int?,
    val data: List<PlantDataObject>?
)