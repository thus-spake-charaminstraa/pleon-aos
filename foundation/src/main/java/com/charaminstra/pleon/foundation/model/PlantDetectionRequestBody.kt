package com.charaminstra.pleon.foundation.model

data class PlantDetectionRequestBody(
    val image_url: String?
)

data class PlantDetectionResponseBody(
    val success: Boolean?,
    val image_url: String?,
    val category: String?,
    val score: String?,
    val species: PlantSpeciesDataObject?
)