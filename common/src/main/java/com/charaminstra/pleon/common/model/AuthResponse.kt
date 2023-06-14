package com.charaminstra.pleon.common.model

data class AuthResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: UserDataObject?
)


