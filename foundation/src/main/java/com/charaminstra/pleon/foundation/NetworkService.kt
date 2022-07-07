package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.model.SmsModel
import com.charaminstra.pleon.foundation.model.SmsResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkService{
    private const val BASE_URL ="http://43.200.108.240:8000"

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
    fun provideApiService(retrofit: Retrofit): APIInterface {
        return retrofit.create(APIInterface::class.java)
    }

    //retrofit 객체 생성
//    val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()

    // 서비스 객체 얻기
//    val api: APIInterface = retrofit.create(APIInterface::class.java)
}


interface APIInterface{
//    @POST("auth/send-sms")
//    fun test(): Call<PhoneModel>

    //test
//    @POST("auth/verify-sms")
//    fun test(
//        @Body smsModel: SmsModel
//    ): Call<SmsResponse>
    @POST("auth/verify-sms")
    suspend fun test(
        @Body smsModel: SmsModel
    ): Response<SmsResponse>
}

interface UsersJoinLoginRepository {
    suspend fun postSignIn(phone: String, code: String): Response<SmsResponse>
}