package com.charaminstra.pleon.common.data

data class PlantResponse(
    val success: Boolean,
    val statusCode: Int?,
    val data: PlantDataObject?
)