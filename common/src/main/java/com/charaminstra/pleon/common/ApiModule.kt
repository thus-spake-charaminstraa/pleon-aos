package com.charaminstra.pleon.common

import com.charaminstra.pleon.common.api.FeedAPIService
import com.charaminstra.pleon.common.api.AuthAPIService
import com.charaminstra.pleon.common.api.UserAPIService
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

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserAPIService {
        return retrofit.create(UserAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthAPIService {
        return retrofit.create(AuthAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideCommentApiService(retrofit: Retrofit): com.charaminstra.pleon.common.api.CommentAPIService {
        return retrofit.create(com.charaminstra.pleon.common.api.CommentAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideNotiApiService(retrofit: Retrofit): com.charaminstra.pleon.common.api.NotiAPIService {
        return retrofit.create(com.charaminstra.pleon.common.api.NotiAPIService::class.java)
    }

    @Provides
    @Singleton
    fun providePlantApiService(retrofit: Retrofit): com.charaminstra.pleon.common.api.PlantAPIService {
        return retrofit.create(com.charaminstra.pleon.common.api.PlantAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideCalendarApiService(retrofit: Retrofit): com.charaminstra.pleon.common.api.ScheduleAPIService {
        return retrofit.create(com.charaminstra.pleon.common.api.ScheduleAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideInferenceApiService(retrofit: Retrofit): com.charaminstra.pleon.common.api.InferenceAPIService {
        return retrofit.create(com.charaminstra.pleon.common.api.InferenceAPIService::class.java)
    }
}