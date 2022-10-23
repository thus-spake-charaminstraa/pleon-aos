package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.api.NotiAPIService
import com.charaminstra.pleon.foundation.api.PleonPreference
import com.charaminstra.pleon.foundation.api.ScheduleAPIService
import com.charaminstra.pleon.foundation.model.NotiRequestBody
import com.charaminstra.pleon.foundation.model.NotiResponse
import javax.inject.Inject

class NotiRepository @Inject constructor(private val service: NotiAPIService, private val prefs: PleonPreference)  {
    suspend fun getNotiList() = service.getNoti(prefs.getAccessToken())

    suspend fun postNotiAction(notiId: String, type: String)
    = service.postNotiAction(prefs.getAccessToken(), NotiRequestBody(notiId, type))
}