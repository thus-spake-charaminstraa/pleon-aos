package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.api.CommentAPIService
import com.charaminstra.pleon.foundation.api.PlantAPIService
import com.charaminstra.pleon.foundation.api.PleonPreference
import com.charaminstra.pleon.foundation.api.ScheduleAPIService
import com.charaminstra.pleon.foundation.model.CommentRequestBody
import com.charaminstra.pleon.foundation.model.ScheduleRequestBody
import javax.inject.Inject

class CommentRepository @Inject constructor(private val service: CommentAPIService, private val prefs: PleonPreference)  {
    suspend fun getComment(feedId :String) = service.getComment(
        prefs.getAccessToken(),
        feedId
    )
}