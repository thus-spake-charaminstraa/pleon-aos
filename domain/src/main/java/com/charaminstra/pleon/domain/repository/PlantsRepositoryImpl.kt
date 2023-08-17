package com.charaminstra.pleon.domain.repository

import com.charaminstra.pleon.common.api.PlantAPIService
import com.charaminstra.pleon.common.api.PleonPreference
import com.charaminstra.pleon.domain.mapper.toDomain
import com.charaminstra.pleon.domain.model.Plant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlantsRepositoryImpl @Inject constructor(
    private val service: PlantAPIService,
    private val prefs: PleonPreference
): PlantsRepository {

    override fun getPlants(): Flow<List<Plant>> = flow {
        val response = service.getPlants(prefs.getAccessToken()).body()?.data!!
        emit(
            response.toDomain()
        )
    }
}