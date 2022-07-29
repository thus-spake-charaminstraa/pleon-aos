package com.charaminstra.pleon.foundation.model

data class PlantRegisterResponse(
    val success: Boolean,
    val statusCode: Int?,
    val dataObject: PlantDataObject?
)