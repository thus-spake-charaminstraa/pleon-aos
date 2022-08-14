package com.charaminstra.pleon.plant_register.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.PlantsRepository
import com.charaminstra.pleon.foundation.model.PlantSpeciesDataObject
import com.charaminstra.pleon.foundation.model.ViewObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantSearchViewModel  @Inject constructor(
    private val plantsRepository: PlantsRepository
): ViewModel() {
    private val TAG = javaClass.name

    private val _plantSpeciesList = MutableLiveData<List<PlantSpeciesDataObject>>()
    val plantSpeciesList : LiveData<List<PlantSpeciesDataObject>> = _plantSpeciesList

    fun getPlantSpecies(){
        viewModelScope.launch {
            val data = plantsRepository.getPlantSpecies()
            when (data.isSuccessful) {
                true -> {
                    _plantSpeciesList.postValue(data.body()?.data!!)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

}