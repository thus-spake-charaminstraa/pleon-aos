package com.charaminstra.pleon.common.data

data class UserCreateResponse(
    val success: Boolean?,
    val statusCode: Int?,
    val data: UserCreateDataObejct?
)

data class UserCreateDataObejct(
    val user : UserObject,
    val token : TokenObject
)

data class UserObject(
    val id: String,
    val nickname: String,
)

data class TokenObject(
    val access_token: String,
    val refresh_token: String
)