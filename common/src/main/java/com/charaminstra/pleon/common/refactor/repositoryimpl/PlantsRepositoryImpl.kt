package com.charaminstra.pleon.common.refactor.repositoryimpl

import com.charaminstra.pleon.common.api.PlantAPIService
import com.charaminstra.pleon.common.api.PleonPreference
import com.charaminstra.pleon.common.refactor.mapper.toDomain
import com.charaminstra.pleon.domain.model.Plant
import com.charaminstra.pleon.domain.repository.PlantsRepository
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