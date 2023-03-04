package com.charaminstra.pleon.common.api

import com.charaminstra.pleon.common.data.ScheduleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ScheduleAPIService {
    @GET("feed/calendar")
    suspend fun getSchedule(
        @Header("Authorization") accessToken: String,
        @Query("plant_id") plantId: String,
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Response<com.charaminstra.pleon.common.data.ScheduleResponse>
}