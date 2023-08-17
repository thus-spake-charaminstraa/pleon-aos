package com.charaminstra.pleon.domain.di

import com.charaminstra.pleon.domain.repository.PlantsRepositoryImpl
import com.charaminstra.pleon.domain.repository.PlantsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsPlantsRepository(
        topicsRepository: PlantsRepositoryImpl,
    ): PlantsRepository

}