package com.charaminstra.pleon.foundation.api

import com.charaminstra.pleon.foundation.model.ScheduleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ScheduleAPIService {
    @GET("schedule")
    suspend fun getSchedule(
        @Header("Authorization") accessToken: String,
        @Query("plant_id") plantId: String,
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Response<ScheduleResponse>
}