package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.api.PlantAPIService
import com.charaminstra.pleon.foundation.api.PleonPreference
import com.charaminstra.pleon.foundation.api.ScheduleAPIService
import com.charaminstra.pleon.foundation.model.ScheduleRequestBody
import javax.inject.Inject

class ScheduleRepository @Inject constructor(private val service: ScheduleAPIService, private val prefs: PleonPreference)  {
    suspend fun getSchedule(plantId :String) = service.getSchedule(prefs.getAccessToken(), ScheduleRequestBody(plantId))
}