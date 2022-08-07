package com.charaminstra.pleon.adapter

enum class ActionType(val action: String, val desc: String){
    물("water", "물을 마셨습니다."),
    통풍("air", "바람을 쐬었습니다."),
    분무("spray","(분무를 해주었습니다.)"),
    분갈이("repot","분갈이를 했습니다."),
    가지치기("prune", "가지치기를 했습니다."),
    잎("leaf", "새 잎이 돋았습니다."),
    꽃("flower", "새 꽃이 피었습니다."),
    영양제("fertilize", "영양제를 먹었습니다."),
    기타("etc", "")
}

data class ActionObject(
    val actionType: ActionType,
    val actionImage: Int)