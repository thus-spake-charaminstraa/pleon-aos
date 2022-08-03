package com.charaminstra.pleon.adapter

enum class ActionType(val action: String){
    물("water"),
    통풍("air"),
    분무("spray"),
    분갈이("repot"),
    가지치기("prune"),
    잎("leaf"),
    꽃("flower"),
    영양제("fertilize"),
    기타("etc")
}

data class ActionObject(
    val actionType: ActionType,
    val actionImage: Int)