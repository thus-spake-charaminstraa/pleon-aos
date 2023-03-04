package com.charaminstra.pleon.common.data

data class PlantsResponse(
    val success: Boolean,
    val statusCode: Int?,
    val data: List<PlantDataObject>?
)