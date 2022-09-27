package com.charaminstra.pleon.foundation.model

data class NotiResponse(
    val data: NotiData?,
    val statusCode: Int?,
    val success: Boolean?
)

data class NotiListResponse(
    val data: List<NotiViewTypeData>,
    val statusCode: Int?,
    val success: Boolean?
)

data class NotiViewTypeData(
    val viewType: String,
    val viewObject: NotiData
)

data class NotiData(
    val id: String,
    val content: String?,
    val kind: String?,
    val plant_id: String?,
)