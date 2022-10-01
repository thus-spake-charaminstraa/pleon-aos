package com.charaminstra.pleon.foundation.model

data class PlantSpeciesResponse(
    val data: List<PlantSpeciesDataObject>
)

data class PlantSpeciesDataObject(
    val id: String,
    val name: String,
)
