package com.charaminstra.pleon.common.model

data class CommentRequestBody(
    val feed_id: String,
    val author_kind: String,
    val content: String
)
