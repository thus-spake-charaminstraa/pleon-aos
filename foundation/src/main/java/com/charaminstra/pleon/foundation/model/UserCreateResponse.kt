package com.charaminstra.pleon.foundation.model

data class UserCreateResponse(
    var success: Boolean?,
    var statusCode: Int?,
    var data: UserCreateDataObejct?

)

data class UserCreateDataObejct(
    var user : UserObject,
    var token : TokenObject
)

data class UserObject(
    var id: String,
    var nickname: String,
)

data class TokenObject(
    var access_token: String,
    var refresh_token: String
)