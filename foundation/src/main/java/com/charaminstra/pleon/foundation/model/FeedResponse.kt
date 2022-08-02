package com.charaminstra.pleon.foundation.model

data class FeedResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: FeedDataObject?
)

data class FeedDataObject(
    val result: List<ResultObject>,
    val count: Int?,
    val next_offset: Int?
)

data class ResultObject(
    val id: String,
    val plant_id: String,
    val publish_date: String,
    val kind: String,
    val content: String,
    val image_url: String,
    val plant: PlantDataObject
)