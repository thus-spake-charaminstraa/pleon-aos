package com.charaminstra.pleon.common.repository

import com.charaminstra.pleon.common.api.UserAPIService
import com.charaminstra.pleon.common.api.PleonPreference
import com.charaminstra.pleon.common.model.DeviceTokenRequestBody
import com.charaminstra.pleon.common.model.UserRequestBody
import com.charaminstra.pleon.common.model.UserCreateResponse
import com.charaminstra.pleon.common.model.UserPushSettingRequestBody
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val service: UserAPIService, private val prefs: PleonPreference){
    /* phone user create */
    suspend fun postPhoneNickname(name: String): Response<UserCreateResponse>{
        return service.postPhoneName(prefs.getVerifyToken(),
            UserRequestBody(name, null)
        )
    }

    /* kakao user create */
    suspend fun postKakaoNickname(name: String): Response<UserCreateResponse>{
        return service.postKakaoName(prefs.getVerifyToken(),
            UserRequestBody(name, null)
        )
    }

    /* user data edit */
    suspend fun patchUserData(name: String, thumbnail: String)
    = service.patchUserData(
        prefs.getAccessToken(),
        UserRequestBody(name, thumbnail)
    )

    suspend fun patchUserPushSetting(comment: Boolean, guide: Boolean)
    = service.patchUserPushSetting(
        prefs.getAccessToken(),
        UserPushSettingRequestBody(comment, guide)
    )

    suspend fun postDeviceToken() = service.postDeviceToken(prefs.getAccessToken(),
        DeviceTokenRequestBody(prefs.getDeviceToken())
    )

    suspend fun deleteDeviceToken() = service.deleteDeviceToken(
        prefs.getAccessToken(),
        prefs.getDeviceToken()
    )
}