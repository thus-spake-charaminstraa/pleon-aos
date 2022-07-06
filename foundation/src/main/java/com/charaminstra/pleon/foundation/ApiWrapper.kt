//package com.charaminstra.pleon.foundation
//
//import android.content.ContentValues.TAG
//import android.util.Log
//import com.charaminstra.pleon.foundation.model.SmsModel
//import com.charaminstra.pleon.foundation.model.SmsResponse
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class ApiWrapper {
//    companion object{
//        fun postTest(phone: String, code: String, callback: (SmsResponse) -> Unit) {
//            val model = SmsModel(phone, code)
//            Log.i(TAG, model.toString())
//            val modelCall = NetWorkService.api.test(model)
//            modelCall.enqueue(object : Callback<SmsResponse> {
//                override fun onResponse(call: Call<SmsResponse>, response: Response<SmsResponse>) {
//                    val response = response.body()
//                    Log.i(TAG, "*success*\n response body: $response")
//                    response?.let {
//                        callback.invoke(it)
//                    }
//                }
//
//                override fun onFailure(call: Call<SmsResponse>, t: Throwable) {
//                    Log.i(TAG, "*fail* : $t")
//                    modelCall.cancel()
//                }
//            })
//        }
//    }
//}