package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.pleon_url
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {
    private const val BASE_URL = pleon_url

    @Provides
    @Singleton
    fun provideRequestHttpLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .connectTimeout(20,TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .writeTimeout(20,TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
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
    fun providePlantApiService(retrofit: Retrofit): PlantAPIService {
        return retrofit.create(PlantAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageApiService(retrofit: Retrofit): ImageAPIService {
        return retrofit.create(ImageAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideFeedApiService(retrofit: Retrofit): FeedAPIService {
        return retrofit.create(FeedAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideCalendarApiService(retrofit: Retrofit): ScheduleAPIService {
        return retrofit.create(ScheduleAPIService::class.java)
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
    fun provideInferenceApiService(retrofit: Retrofit): InferenceAPIService {
        return retrofit.create(InferenceAPIService::class.java)
    }
}