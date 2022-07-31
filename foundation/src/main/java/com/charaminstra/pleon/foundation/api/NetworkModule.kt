package com.charaminstra.pleon.foundation.api

import android.util.Log
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
object NetWorkModule{
    private const val BASE_URL ="https://43kxp4xkrk.execute-api.ap-northeast-2.amazonaws.com/beta/"
    //private const val BASE_URL ="http://43.200.108.240:8000"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
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
    fun provideApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
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
}