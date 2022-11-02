package com.charaminstra.pleon.common

import com.charaminstra.pleon.foundation.model.*
import java.util.*

data class FeedListResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: FeedDataObject
)

data class FeedDataObject(
    val result: ArrayList<ResultObject>,
    val count: Int,
    val next_offset: Int,
    val isLast: Boolean,
)

data class ResultObject(
    val viewType: String,
    val viewObject: FeedViewObject?
)

data class FeedViewObject(
    val id: String,
    val plant_id: String,
    val publish_date: Date,
    val kind: ActionType,
    val content: String,
    val image_url: String?,
    val plant: PlantDataObject?,
    val user: UserDataObject,
    val comments: List<CommentObject>?,
    val symptoms: List<SymptomObject>?,
    val causes: List<CauseObject>?,
    val created_at: Date,
)