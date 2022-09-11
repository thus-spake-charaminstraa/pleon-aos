package com.charaminstra.pleon.plant_register

enum class AirType(val apiString: String, val descId: Int){
    YES("yes", R.string.plant_air_fragment_one),
    WINDOW("window",R.string.plant_air_fragment_two),
    NO("no",R.string.plant_air_fragment_three),
}