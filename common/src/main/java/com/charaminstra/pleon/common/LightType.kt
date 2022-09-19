package com.charaminstra.pleon.common

enum class LightType(val apiString: String,val descId: String){
    BRIGHT("bright","창문 가까이 직사광선을 받아요"),
    HALF_BRIGHT("half_bright","창문 안쪽에서 간접광을 받아요"),
    LAMP("lamp","실내에서 식물 조명 빛을 받아요"),
    DARK("dark","실내에서 n해를 못 받아요"),;
}