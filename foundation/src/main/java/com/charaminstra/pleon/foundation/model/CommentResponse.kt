package com.charaminstra.pleon.foundation.model

data class CommentResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: List<CommentObject>
)
