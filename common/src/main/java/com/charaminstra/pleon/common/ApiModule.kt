package com.charaminstra.pleon.common

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideFeedApiService(retrofit: Retrofit): FeedAPIService {
        return retrofit.create(FeedAPIService::class.java)
    }
}