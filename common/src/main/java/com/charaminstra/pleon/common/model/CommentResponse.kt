package com.charaminstra.pleon.common.model

import java.util.*

data class CommentResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: List<CommentObject>?
)

data class CommentPostResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: CommentObject?
)

data class CommentObject(
    val id: String?,
    val feed_id: String?,
    val user_id: String?,
    val plant_id: String?,
    val author_kind: String?,
    val content: String?,
    val created_at: Date?,
    val updated_at: Date?,
    val user: UserDataObject?,
    val plant: PlantDataObject?
)