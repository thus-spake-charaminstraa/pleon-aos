package com.charaminstra.pleon.plant_register.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.PlantsRepository
import com.charaminstra.pleon.foundation.model.PlantSpeciesDataObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class PlantSearchViewModel  @Inject constructor(
    private val plantsRepository: PlantsRepository
): ViewModel() {
    private val TAG = javaClass.name

    private val _plantSpeciesList = MutableLiveData<List<PlantSpeciesDataObject>>()
    val plantSpeciesList : LiveData<List<PlantSpeciesDataObject>> = _plantSpeciesList

    private val _searchResult = MutableLiveData<List<PlantSpeciesDataObject>>()
    val searchResult : LiveData<List<PlantSpeciesDataObject>> = _searchResult

    fun getPlantSpecies(){
        viewModelScope.launch {
            val data = plantsRepository.getPlantSpecies()
            Log.i(TAG,"data -> "+ data.body().toString())
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

    fun searchFilter(filterQuery: String){
        val filteredList = ArrayList<PlantSpeciesDataObject>()
        for (current in plantSpeciesList.value!!) {
            if (current.name.toLowerCase(Locale.getDefault()).contains(filterQuery)) {
                filteredList.add(current)
            }
        }
        _searchResult.postValue(filteredList)
    }

}