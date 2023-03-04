package com.charaminstra.pleon.common

import com.charaminstra.pleon.common.api.*
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
    fun provideImageApiService(retrofit: Retrofit): ImageAPIService {
        return retrofit.create(ImageAPIService::class.java)
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
    fun provideCommentApiService(retrofit: Retrofit): CommentAPIService {
        return retrofit.create(CommentAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideNotiApiService(retrofit: Retrofit): NotiAPIService {
        return retrofit.create(NotiAPIService::class.java)
    }

    @Provides
    @Singleton
    fun providePlantApiService(retrofit: Retrofit): PlantAPIService {
        return retrofit.create(PlantAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideCalendarApiService(retrofit: Retrofit): ScheduleAPIService {
        return retrofit.create(ScheduleAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideInferenceApiService(retrofit: Retrofit): InferenceAPIService {
        return retrofit.create(InferenceAPIService::class.java)
    }
}