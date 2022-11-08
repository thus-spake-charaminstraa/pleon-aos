package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.api.AuthAPIService
import com.charaminstra.pleon.foundation.api.PleonPreference
import com.charaminstra.pleon.foundation.model.*
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val service: AuthAPIService, private val prefs: PleonPreference)  {
    suspend fun getAuth() = service.getAuth(prefs.getAccessToken())
    suspend fun getRefresh() = service.getAuth(prefs.getRefreshToken())

    suspend fun postPhoneNum(phone: String) = service.postPhone(SmsRequestBody(phone))

    suspend fun postCode(phone: String, code:String): Response<SmsResponse> {
        val model = SmsCodeRequestBody(phone, code)
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