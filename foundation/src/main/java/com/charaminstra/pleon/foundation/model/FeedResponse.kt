package com.charaminstra.pleon.foundation.model

import java.util.*

data class FeedResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: FeedDataObject?
)

data class FeedDataObject(
    val result: ArrayList<ResultObject>,
    val count: Int?,
    val next_offset: Int?
)

data class ResultObject(
    val id: String,
    val plant_id: String,
    val publish_date: Date,
    val kind: String,
    val content: String,
    val image_url: String?,
    val plant: PlantDataObject
)