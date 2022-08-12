package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.ScheduleRequestBody
import com.charaminstra.pleon.foundation.model.ScheduleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface ScheduleAPIService {
    @GET("schedule")
    suspend fun getSchedule(
        @Header("Authorization") accessToken: String,
        @Body scheduleRequestBody: ScheduleRequestBody
    ): Response<ScheduleResponse>
}