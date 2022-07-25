package com.charaminstra.pleon.plant_register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantRegisterViewModel @Inject constructor(private val repository: PlantRegisterRepository) : ViewModel() {

    private val TAG = javaClass.name
    private val _plantRegisterSuccess = MutableLiveData<Boolean>()
    val plantRegisterSuccess : LiveData<Boolean> = _plantRegisterSuccess

    val name = MutableLiveData<String>()
    val species = MutableLiveData<String>()
    val water_date = MutableLiveData<String>()
    val adopt_date = MutableLiveData<String>()
    val light = MutableLiveData<String>()
    val air = MutableLiveData<String>()

    fun getName(): LiveData<String> {
        return name
    }
    fun setName(value: String) {
        name.value = value
    }
    fun getSpecies(): LiveData<String> {
        return species
    }
    fun setSpecies(value: String) {
        species.value = value
    }
    fun getWater_date(): LiveData<String> {
        return water_date
    }
    fun setWater_date(value: String) {
        water_date.value = value
    }
    fun getAdopt_date(): LiveData<String> {
        return adopt_date
    }
    fun setAdopt_date(value: String) {
        adopt_date.value = value
    }
    fun getLight(): LiveData<String> {
        return light
    }
    fun setLight(value: String) {
        light.value = value
    }
    fun getAir(): LiveData<String> {
        return air
    }
    fun setAir(value: String) {
        air.value = value
    }

    fun postPlant(){
        viewModelScope.launch {
            val data = repository.postPlant(
                getName().value.toString(),
                getSpecies().value.toString(),
                getWater_date().value.toString(),
                getAdopt_date().value.toString(),
                getLight().value.toString(),
                getAir().value.toString())
            _plantRegisterSuccess.postValue(data.body()?.success)
            Log.i(TAG,"DATA -> $data"+"\n"+data)
            Log.i(TAG,"DATA.body -> $data"+"\n"+data.body())
//            when (data.body()?.success) {
//                true -> {
//                }
//                else -> {
//                }
//            }
        }
    }
}