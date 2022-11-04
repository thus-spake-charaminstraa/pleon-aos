package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.api.UserAPIService
import com.charaminstra.pleon.foundation.api.PleonPreference
import com.charaminstra.pleon.foundation.model.DeviceTokenRequestBody
import com.charaminstra.pleon.foundation.model.UserRequestBody
import com.charaminstra.pleon.foundation.model.UserCreateResponse
import com.charaminstra.pleon.foundation.model.UserPushSettingRequestBody
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val service: UserAPIService, private val prefs: PleonPreference){
    /* user create */
    suspend fun postNickname(name: String): Response<UserCreateResponse>{
        return service.postName(prefs.getVerifyToken(), UserRequestBody(name,null)
        )
    }

    /* user data edit */
    suspend fun patchUserData(name: String, thumbnail: String)
    = service.patchUserData(
        prefs.getAccessToken(),
        UserRequestBody(name,thumbnail)
    )

    suspend fun patchUserPushSetting(comment: Boolean, guide: Boolean)
    = service.patchUserPushSetting(
        prefs.getAccessToken(),
        UserPushSettingRequestBody(comment, guide)
    )

    suspend fun postDeviceToken() = service.postDeviceToken(prefs.getAccessToken(),
        DeviceTokenRequestBody(prefs.getDeviceToken())
    )
}