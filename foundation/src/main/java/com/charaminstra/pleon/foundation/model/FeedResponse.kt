package com.charaminstra.pleon.foundation.model

data class FeedResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: List<FeedDataObject?>
)

data class FeedDataObject(
    val id: String,
    val plant_id: String,
    val publish_date: String,
    val kind: String,
    val content: String,
    val image_url: String,
)