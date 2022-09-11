package com.charaminstra.pleon.plant_register

enum class LightType(val apiString: String,val descId: Int){
    BRIGHT("bright", R.string.plant_light_fragment_one),
    HALF_BRIGHT("half_bright",R.string.plant_light_fragment_two),
    LAMP("lamp",R.string.plant_light_fragment_three),
    DARK("dark",R.string.plant_light_fragment_four),;
}