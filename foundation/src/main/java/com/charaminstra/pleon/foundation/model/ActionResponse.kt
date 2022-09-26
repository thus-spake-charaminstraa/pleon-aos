package com.charaminstra.pleon.foundation.model

data class ActionResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: List<ActionData>?
)

data class ActionData(
    val name_kr: String,
    val name_en: String,
    val icon_uri: String
)


