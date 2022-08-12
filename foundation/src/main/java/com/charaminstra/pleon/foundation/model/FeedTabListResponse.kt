package com.charaminstra.pleon.foundation.model

import java.util.*

data class FeedTabListResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: FeedTabDataObject
)

data class FeedTabDataObject(
 val result: List<ResultObject>,
 val count: Int,
 val next_offset: Int
)

data class ResultObject(
    val viewType: String,
    val viewObject: ViewObject
)

data class ViewObject(
    val id: String,
    val plant_id: String,
    val publish_date: Date,
    val kind: String,
    val content: String,
    val image_url: String?,
    val plant: PlantDataObject
)


