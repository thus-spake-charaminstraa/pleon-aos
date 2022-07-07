package com.charaminstra.pleon.login

import com.charaminstra.pleon.foundation.APIInterface
import com.charaminstra.pleon.foundation.UsersJoinLoginRepository
import com.charaminstra.pleon.foundation.model.SmsRequestBody
import com.charaminstra.pleon.foundation.model.SmsResponse
import retrofit2.Response
import javax.inject.Inject

class TestRepository @Inject constructor(
    private val service: APIInterface
) : UsersJoinLoginRepository {

    override suspend fun postSignIn(phone: String, code:String): Response<SmsResponse> {
        val model = SmsRequestBody(phone, code)
        return service.test(model)
    }
}