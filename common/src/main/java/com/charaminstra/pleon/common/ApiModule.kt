package com.charaminstra.pleon.common

import com.charaminstra.pleon.common.api.FeedAPIService
import com.charaminstra.pleon.foundation.api.AuthAPIService
import com.charaminstra.pleon.foundation.api.UserAPIService
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

    @Provides
    @Singleton
    fun provideImageApiService(retrofit: Retrofit): com.charaminstra.pleon.common.api.ImageAPIService {
        return retrofit.create(com.charaminstra.pleon.common.api.ImageAPIService::class.java)
    }
}