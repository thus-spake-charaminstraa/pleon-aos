package com.charaminstra.pleon.foundation.model

data class PlantRegisterRequestBody(
    val name: String,
    val species: String,
    val adopt_date: String,
    val water_date: String,
    val thumbnail: String,
    val light: String,
    val air: String
)

data class PlantEditRequestBody(
    val name: String,
    val adopt_date: String,
    val thumbnail: String,
    val light: String,
    val air: String
)

