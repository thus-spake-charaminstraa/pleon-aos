package com.charaminstra.pleon.common

enum class ActionType(val action: String, val desc: String){
    오늘의모습("today", ""),
    물("water", "(이)가 물을 마셨습니다."),
    통풍("air", "(이)가 바람을 쐬었습니다."),
    분무("spray","(이)에게 분무를 해주었습니다."),
    분갈이("repot","(이)가 분갈이를 했습니다."),
    가지치기("prune", "(이)가 가지치기를 했습니다."),
    잎("leaf", "(이)가 새 잎이 돋았습니다."),
    꽃("flower", "(이)가 새 꽃이 피었습니다."),
    영양제("nutrition", "(이)가 영양제를 먹었습니다."),
    열매("fruit","열매가 맺혔다"),
    기타("etc", "")
}

data class ActionObject(
    val actionType: ActionType,
    val actionImage: Int)