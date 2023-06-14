package com.charaminstra.pleon.common.model

data class FeedRequestBody(
    val plant_id: String,
    val publish_date: String,
    val kind: String,
    val content: String,
    val image_url: String?
)

