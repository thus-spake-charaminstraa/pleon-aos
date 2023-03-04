package com.charaminstra.pleon.common.data

data class GuideResponse(
    val data: GuideData?,
    val statusCode: Int?,
    val success: Boolean?
)

data class GuideListResponse(
    val data: List<GuideViewTypeData>,
    val statusCode: Int?,
    val success: Boolean?
)

data class GuideViewTypeData(
    val viewType: String,
    val viewObject: GuideData
)

data class GuideData(
    val id: String,
    val content: String?,
    val kind: String?,
    val plant_id: String?,
)