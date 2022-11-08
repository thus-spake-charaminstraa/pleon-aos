package com.charaminstra.pleon.common

import com.google.gson.annotations.SerializedName

enum class ActionType(val resId : Int?){
    @SerializedName("event")
    EVENT(R.string.event),
    @SerializedName("today")
    TODAY(R.string.today),
    @SerializedName("water")
    WATER(R.string.water),
    @SerializedName("air")
    AIR(R.string.air),
    @SerializedName("spray")
    SPRAY(R.string.spray),
    @SerializedName("repot")
    REPOT(R.string.repot),
    @SerializedName("prune")
    PRUNE(R.string.prune),
    @SerializedName("leaf")
    LEAF(R.string.leaf),
    @SerializedName("flower")
    FLOWER(R.string.flower),
    @SerializedName("nutrition")
    NUTRITION(R.string.nutrition),
    @SerializedName("fruit")
    FRUIT(R.string.fruit),
    @SerializedName("etc")
    ETC(R.string.etc)
//    오늘의모습("today", ""),
//    물("water", "(이)가 물을 마셨습니다."),
//    통풍("air", "(이)가 바람을 쐬었습니다."),
//    분무("spray","(이)에게 분무를 해주었습니다."),
//    분갈이("repot","(이)가 분갈이를 했습니다."),
//    가지치기("prune", "(이)가 가지치기를 했습니다."),
//    잎("leaf", "(이)가 새 잎이 돋았습니다."),
//    꽃("flower", "(이)가 새 꽃이 피었습니다."),
//    영양제("nutrition", "(이)가 영양제를 먹었습니다."),
//    열매("fruit","열매가 맺혔다"),
//    기타("etc", "")
}