package com.charaminstra.pleon.common.repository

import com.charaminstra.pleon.common.api.AuthAPIService
import com.charaminstra.pleon.common.data.KaKaoRequestBody
import com.charaminstra.pleon.common.data.LoginResponse
import com.charaminstra.pleon.common.data.SmsResponse
import com.charaminstra.pleon.common.api.PleonPreference
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val service: AuthAPIService, private val prefs: PleonPreference)  {
    suspend fun getAuth() = service.getAuth(prefs.getAccessToken())
    suspend fun getRefresh() = service.getAuth(prefs.getRefreshToken())

    suspend fun postPhoneNum(phone: String) = service.postPhone(
        com.charaminstra.pleon.common.data.SmsRequestBody(
            phone
        )
    )

    suspend fun postCode(phone: String, code:String): Response<SmsResponse> {
        val model = com.charaminstra.pleon.common.data.SmsCodeRequestBody(phone, code)
        return service.postCode(model)
    }

    suspend fun postLogin() : Response<LoginResponse> {
        return service.postLogin(prefs.getVerifyToken())
    }

    /* kakao */
    suspend fun postKakaoToken(kakaoToken : String): Response<SmsResponse> {
        return service.postKakaoToken(KaKaoRequestBody(kakaoToken))
    }
}