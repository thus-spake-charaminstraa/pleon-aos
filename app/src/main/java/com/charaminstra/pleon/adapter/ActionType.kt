package com.charaminstra.pleon.adapter

enum class ActionType{
    물, 통풍, 분무, 분갈이, 가지치기, 잎, 꽃, 영양제, 기타
}

class actionToKindType(val actionType: ActionType){
    override fun toString(): String {
        return when(actionType){
            ActionType.물 -> "water"
            ActionType.통풍 -> "air"
            ActionType.분무 -> "spray"
            ActionType.분갈이 -> "repot"
            ActionType.가지치기 -> "prune"
            ActionType.잎 -> "leaf"
            ActionType.꽃 -> "flower"
            ActionType.영양제 -> "fertilize"
            ActionType.기타 -> "etc"
        }
    }
}

data class ActionObject(
    val actionType: ActionType,
    val actionImage: String)