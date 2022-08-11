package com.charaminstra.pleon.foundation.model

data class CommentRequestBody(
    val feed_id: String,
    val plant_id: String,
    val author_kind: String,
    val content: String
)
