package com.charaminstra.pleon.common.data

data class CommentRequestBody(
    val feed_id: String,
    val author_kind: String,
    val content: String
)
