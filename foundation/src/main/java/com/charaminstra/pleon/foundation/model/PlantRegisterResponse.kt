package com.charaminstra.pleon.foundation.model

data class PlantRegisterResponse(
    val success: Boolean,
    val statusCode: Int,
    val dataObject: PlantRegisterDataObject?
)

data class PlantRegisterDataObject(
    val id: String?,
    val name: String?,
    val owner: String?,
    val species: String?,
    val adopt_date: String?,
    val thumbnail: String?,
    val location: String?
)