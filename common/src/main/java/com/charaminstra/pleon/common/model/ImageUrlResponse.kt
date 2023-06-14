package com.charaminstra.pleon.foundation.model

data class ImageUrlResponse(
    val data: UrlObject?,
    val statusCode: Int,
    val success: String
)

data class UrlObject(
    val url: String
)
