package com.charaminstra.pleon.foundation.model

data class FeedResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: FeedDataObject?
)

data class FeedDataObject(
    val publish_date: String,
    val kind: String,
    val content: String,
    val image_url: String,
)