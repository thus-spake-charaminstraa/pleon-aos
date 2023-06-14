package com.charaminstra.pleon.common.model

data class PlantResponse(
    val success: Boolean,
    val statusCode: Int?,
    val data: PlantDataObject?
)