package com.charaminstra.pleon.foundation.model

data class UserCreateResponse(
    var user : UserObject,
    var token : TokenObject
)

data class UserObject(
    var id: String,
    var nickname: String,
    var phone: String
)

data class TokenObject(
    var access_token: String,
    var refresh_token: String
)