package com.charaminstra.pleon.plant_register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlantRegisterViewModel @Inject constructor(private val repository: PlantRegisterRepository) : ViewModel() {

    private val TAG = javaClass.name
    private val _plantRegisterSuccess = MutableLiveData<Boolean>()
    val plantRegisterSuccess : LiveData<Boolean> = _plantRegisterSuccess
    fun getData() = plantRegisterSuccess

    init {
        loadData()
    }

    fun loadData(){
        viewModelScope.launch {
//            val data = repository.postPlant()
//            Log.i(TAG, "data -> $data")
//            when (data.isSuccessful) {
//                true -> {
//                    _authSuccess.postValue(true)
//                    Log.i(TAG,"SUCCESS -> "+ data.toString())
//                }
//                else -> {
//                    _authSuccess.postValue(false)
//                    Log.i(TAG,"FAIL -> "+ data.toString())
//                }
//            }
        }
    }
}