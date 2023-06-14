package com.charaminstra.pleon.common.model

import com.charaminstra.pleon.common.FeedViewObject

data class FeedIdResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: FeedViewObject?
)

