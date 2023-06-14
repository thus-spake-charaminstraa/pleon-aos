package com.charaminstra.pleon.common.repository

import com.charaminstra.pleon.common.api.NotiAPIService
import com.charaminstra.pleon.common.api.PleonPreference
import com.charaminstra.pleon.common.model.GuideRequestBody
import javax.inject.Inject

class NotiRepository @Inject constructor(private val service: NotiAPIService, private val prefs: PleonPreference)  {
    suspend fun getGuideList() = service.getGuideList(prefs.getAccessToken())

    suspend fun postGuideAction(notiId: String, type: String)
    = service.postNotiAction(prefs.getAccessToken(), GuideRequestBody(notiId, type))

    suspend fun getNotiNew() = service.getNotiNew(prefs.getAccessToken())

    suspend fun getNotiList() = service.getNotiList(prefs.getAccessToken())

    suspend fun getNotiDialog() = service.getNotiDialog(prefs.getAccessToken())

    suspend fun postNotiDialogTodayStop() = service.postNotiTodayStop(prefs.getAccessToken())
}