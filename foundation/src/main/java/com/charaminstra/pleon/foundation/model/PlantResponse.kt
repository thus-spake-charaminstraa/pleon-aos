package com.charaminstra.pleon.foundation.model

data class PlantResponse(
    val success: Boolean,
    val statusCode: Int?,
    val data: PlantDataObject?
)