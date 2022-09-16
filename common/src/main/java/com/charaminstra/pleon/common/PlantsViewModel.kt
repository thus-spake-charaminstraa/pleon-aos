package com.charaminstra.pleon.common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.PlantsRepository
import com.charaminstra.pleon.foundation.model.PlantDataObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantsViewModel @Inject constructor(
    private val repository: PlantsRepository) : ViewModel() {
    private val TAG = javaClass.name

    private val _plantsList = MutableLiveData<List<PlantDataObject>>()
    val plantsList : LiveData<List<PlantDataObject>> = _plantsList

    private val _plantsCount = MutableLiveData<Int>()
    val plantsCount : LiveData<Int> = _plantsCount
//
    //fun getData() = plantsList

    init {
        loadData()
    }

    fun loadData(){
        viewModelScope.launch {
            val data = repository.getPlants()
            Log.i(TAG, "data -> $data")
            Log.i(TAG, "data.body -> "+data.body())
            when (data.isSuccessful) {
                true -> {
                    _plantsCount.postValue(data.body()?.data?.size)
                    _plantsList.postValue(data.body()?.data!!)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }
}