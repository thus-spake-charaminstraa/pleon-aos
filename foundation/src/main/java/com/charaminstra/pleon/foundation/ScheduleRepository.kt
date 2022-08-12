package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.api.PleonPreference
import com.charaminstra.pleon.foundation.api.ScheduleAPIService
import javax.inject.Inject

class ScheduleRepository @Inject constructor(private val service: ScheduleAPIService, private val prefs: PleonPreference)  {
    suspend fun getSchedule(plantId :String, year:Int, month:Int)
    = service.getSchedule(
        prefs.getAccessToken(),
        plantId,
        year,
        month)
}