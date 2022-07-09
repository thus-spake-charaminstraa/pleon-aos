package com.charaminstra.pleon.login

import com.charaminstra.pleon.foundation.APIInterface
import com.charaminstra.pleon.foundation.model.SmsRequestBody
import com.charaminstra.pleon.foundation.model.SmsResponse
import retrofit2.Response
import javax.inject.Inject

class SmsRepository @Inject constructor(private val service: APIInterface)  {

    suspend fun postPhoneNum(phone: String) = service.postPhone(SmsRequestBody(phone,""))

    suspend fun postCode(phone: String, code:String): Response<SmsResponse> {
        val model = SmsRequestBody(phone, code)
        return service.postCode(model)
    }
}