package com.charaminstra.pleon.common.model

data class SmsRequestBody(
    val phone: String,
)


data class SmsCodeRequestBody(
    val phone: String,
    val code: String?
)



