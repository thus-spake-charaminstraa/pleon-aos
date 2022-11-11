package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.api.NotiAPIService
import com.charaminstra.pleon.foundation.api.PleonPreference
import com.charaminstra.pleon.foundation.model.GuideRequestBody
import javax.inject.Inject

class NotiRepository @Inject constructor(private val service: NotiAPIService, private val prefs: PleonPreference)  {
    suspend fun getGuideList() = service.getGuideList(prefs.getAccessToken())

    suspend fun postNotiAction(notiId: String, type: String)
    = service.postNotiAction(prefs.getAccessToken(), GuideRequestBody(notiId, type))

    suspend fun getNotiList() = service.getNotiList(prefs.getAccessToken())

    suspend fun getNotiDialog() = service.getNotiDialog(prefs.getAccessToken())

    suspend fun postNotiTodayStop() = service.postNotiTodayStop(prefs.getAccessToken())
}