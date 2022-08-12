package com.charaminstra.pleon.foundation.model

import java.util.*

data class ScheduleResponse (
    val success: Boolean,
    val statusCode: Int,
    val data: List<ScheduleDataObject>
)

data class ScheduleDataObject (
    val timestamp: Date,
    val kinds: List<String>
)