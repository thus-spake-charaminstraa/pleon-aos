package com.charaminstra.pleon.foundation.model

import java.util.*

data class FeedListResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: FeedListDataObject?
)

data class FeedListDataObject(
    val result: List<FeedObject>,
    val count: Int?,
    val next_offset: Int?
)

data class FeedObject(
    val id: String,
    val plant_id: String,
    val publish_date: Date,
    val kind: String,
    val content: String,
    val image_url: String?,
    val plant: PlantDataObject
)