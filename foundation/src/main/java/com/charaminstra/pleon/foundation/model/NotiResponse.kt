package com.charaminstra.pleon.foundation.model

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
    val plant_id: String?,
    val feed_id: String?,
    val comment_id: String?,
    val content: String?,
    val feedContent: String?,
    val feedImageUrl: String?,
)