package com.charaminstra.pleon.common.data

data class PlantSpeciesResponse(
    val data: List<PlantSpeciesDataObject>
)

data class PlantSpeciesDataObject(
    val id: String,
    val name: String,
    val managing_difficulty: String?,
    val benefit: String?,
    val tip: String?
)
