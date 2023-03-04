package com.charaminstra.pleon.common.repository

import com.charaminstra.pleon.common.api.PleonPreference
import com.charaminstra.pleon.common.api.ScheduleAPIService
import javax.inject.Inject

class ScheduleRepository @Inject constructor(private val service: com.charaminstra.pleon.common.api.ScheduleAPIService, private val prefs: com.charaminstra.pleon.common.api.PleonPreference)  {
    suspend fun getSchedule(plantId :String, year:Int, month:Int)
    = service.getSchedule(
        prefs.getAccessToken(),
        plantId,
        year,
        month)
}