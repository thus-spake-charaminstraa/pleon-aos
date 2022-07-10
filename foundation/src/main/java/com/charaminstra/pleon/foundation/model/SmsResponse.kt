package com.charaminstra.pleon.foundation.model

data class SmsResponse(
    var data: DataObject?,
    var success: Boolean?
)

data class DataObject(
    var isExist: Boolean?,
    var verify_token: String
)
