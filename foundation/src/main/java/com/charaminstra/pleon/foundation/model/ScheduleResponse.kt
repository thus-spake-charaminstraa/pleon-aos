package com.charaminstra.pleon.foundation.model

data class ScheduleResponse (
    val success: Boolean,
    val statusCode: Int,
    val data: ScheduleDataObject
)

data class ScheduleDataObject (
    val id: String,
    val plant_id: String,
    val timestamp: String,
    val kind: String
)

/*
* "id": {},
      "plant_id": {},
      "timestamp": "2022-08-05T12:04:53.620Z",
      "kind": "water"
*
* */