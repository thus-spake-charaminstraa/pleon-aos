package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.model.SmsModel
import com.charaminstra.pleon.foundation.model.SmsResponse

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object NetWorkService{
    private const val BASE_URL ="http://43.200.108.240:8000"

    //retrofit 객체 생성
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // 서비스 객체 얻기
    val api: APIInterface = retrofit.create(APIInterface::class.java)
}


interface APIInterface{
//    @POST("auth/send-sms")
//    fun test(): Call<PhoneModel>

    //test
    @POST("auth/verify-sms")
    fun test(
        @Body smsModel: SmsModel
    ): Call<SmsResponse>
}