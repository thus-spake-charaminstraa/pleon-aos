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
    fun getSpecies(): String? {
        return species.value
    }
    fun setSpecies(value: String) {
        species.value = value
    }
    fun getWater_date(): String? {
        return water_date.value
    }
    fun setWater_date(value: String) {
        water_date.value = value
    }
    fun getAdopt_date(): String? {
        return adopt_date.value
    }
    fun setAdopt_date(value: String) {
        adopt_date.value = value
    }
    fun getLight(): String? {
        return light.value
    }
    fun setLight(value: String) {
        light.value = value
    }
    fun getAir(): String? {
        return air.value
    }
    fun setAir(value: String) {
        air.value = value
    }

    suspend fun postPlant(){
        viewModelScope.launch {
            val data = repository.postPlant(
                getName().toString(),
                getSpecies().toString(),
                getWater_date().toString(),
                getAdopt_date().toString(),
                getLight().toString(),
                getAir().toString())
            _plantRegisterSuccess.postValue(data.body()?.success)
            Log.i(TAG,"FAIL -> $data"+"\n"+data)
            when (data.body()?.success) {
                true -> {
                }
                else -> {
                }
            }
        }
    }
}