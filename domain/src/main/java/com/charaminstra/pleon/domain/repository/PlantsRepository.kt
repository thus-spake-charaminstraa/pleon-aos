package com.charaminstra.pleon.domain.repository

import com.charaminstra.pleon.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantsRepository {

    /**
     * 유저가 등록한 식물 리스트
     */
    fun getPlants() : Flow<List<Plant>>

}