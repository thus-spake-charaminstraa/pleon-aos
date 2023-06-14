package com.charaminstra.pleon.domain.usecase

import com.charaminstra.pleon.domain.model.Plant
import com.charaminstra.pleon.domain.repository.PlantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlantListUseCase @Inject constructor(
    private val plantsRepository: PlantsRepository
) {

    operator fun invoke(): Flow<List<Plant>> {
        return plantsRepository.getPlants()
    }
}