package com.charaminstra.pleon.plant_register

enum class AirType(val apiString: String, val descId: Int){
    YES("yes", R.string.air_one),
    WINDOW("window",R.string.air_two),
    NO("no",R.string.air_three),
}