package com.charaminstra.pleon.foundation.model

data class AuthResponse(
    var success: Boolean?,
    var statusCode: Int?,
    var data: authDataObejct?

)

data class authDataObejct(
    var id: String,
    var nickname: String,
    var phone: String
)
