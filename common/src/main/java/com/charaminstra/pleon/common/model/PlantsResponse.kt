package com.charaminstra.pleon.common.model

data class PlantsResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: List<PlantDataObject>?
)