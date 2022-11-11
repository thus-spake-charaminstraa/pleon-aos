package com.charaminstra.pleon.foundation.model

data class NotiDialogResponse(
    val data:  NotiDialogData,
    val statusCode: Int?,
    val success: Boolean?
)

data class NotiDialogData(
    val isExist : Boolean?,
    val notices : List<NotiDialogText>
)

data class NotiDialogText(
    val name: String?,
    val title: String?,
    val content: String?,
    val button: Boolean?,
)
