package com.charaminstra.pleon.foundation.model

data class LoginResponse(
    val success: Boolean?,
    val data: LoginDataObejct?
)

data class LoginDataObejct(
    val user: UserObject,
    val token: TokenObject
    )
