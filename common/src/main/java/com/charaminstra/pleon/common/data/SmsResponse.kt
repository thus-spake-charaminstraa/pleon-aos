package com.charaminstra.pleon.common.data

data class SmsResponse(
    val data: DataObject?,
    val success: Boolean
)

data class DataObject(
    val isExist: Boolean?,
    val verify_token: String?
)
