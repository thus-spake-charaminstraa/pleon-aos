package com.charaminstra.pleon.plant_register

enum class LightType(val apiString: String,val descId: Int){
    BRIGHT("bright", R.string.light_one),
    HALF_BRIGHT("half_bright",R.string.light_two),
    LAMP("lamp",R.string.light_three),
    DARK("dark",R.string.light_four),;
}