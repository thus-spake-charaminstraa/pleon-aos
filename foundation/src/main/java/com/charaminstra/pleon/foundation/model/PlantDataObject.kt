package com.charaminstra.pleon.foundation.model

import java.util.*

data class PlantDataObject(
    val id: String?,
    val name: String?,
    val owner: String?,
    val species: String?,
    val adopt_date: Date?,
    val thumbnail: String?,
    val light: String?,
    val air: String?,
    val d_day: Int?
)