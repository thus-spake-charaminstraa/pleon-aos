package com.charaminstra.pleon.common.refactor.mapper

import com.charaminstra.pleon.common.model.PlantDataObject
import com.charaminstra.pleon.domain.model.Plant

object PlantMapper{
    fun toDomain(data: List<PlantDataObject>): List<Plant>{
        return data.map{
            Plant(
                plantId = it.id,
                plantName = it.name,
                plantImageUrl = it.thumbnail,
                plantDday = it.d_day,
                plantMood = it.mood?.mood,
                plantMoodUri = it.mood?.icon_uri
            )
        }
    }
}

fun List<PlantDataObject>.toDomain() : List<Plant>{
    return PlantMapper.toDomain(this)
}