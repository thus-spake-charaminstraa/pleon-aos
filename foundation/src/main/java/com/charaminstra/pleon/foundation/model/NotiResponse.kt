package com.charaminstra.pleon.foundation.model

data class NotiResponse(
    val data: NotiData?,
    val statusCode: Int?,
    val success: Boolean?
)

data class NotiListResponse(
    val data: List<NotiData>,
    val statusCode: Int?,
    val success: Boolean?
)

data class NotiData(
    val id: String,
    val content: String?,
    val image_url: String?,
    val kind: String?,
    val plant_id: String?,
    val publish_date: String?
)