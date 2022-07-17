package com.charaminstra.pleon.foundation.model

data class AuthResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: authDataObejct?

)

data class authDataObejct(
    val id: String?,
    val nickname: String?,
    val phone: String?
)
