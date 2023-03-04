package com.charaminstra.pleon.common.data

data class UserDataObject(
    val id: String?,
    val nickname: String?,
    val phone: String?,
    val thumbnail: String?,
    val comment_push_noti : Boolean?,
    val guide_push_noti: Boolean?
)
