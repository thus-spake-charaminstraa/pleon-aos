package com.charaminstra.pleon.plant_register

enum class AirType(val apiString: String, val desc: String){
    YES("yes", "항상 잘 통해요"),
    WINDOW("window","창문을 열면 잘 통해요"),
    NO("no","바람이 아예 안통해요"),
}