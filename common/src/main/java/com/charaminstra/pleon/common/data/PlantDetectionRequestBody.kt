package com.charaminstra.pleon.common.data

data class PlantDetectionRequestBody(
    val image_url: String?
)

data class PlantDetectionResponseBody(
    val success: Boolean?,
    val image_url: String?,
    val category: String?,
    val score: Float?,
    val species: PlantSpeciesDataObject?
)