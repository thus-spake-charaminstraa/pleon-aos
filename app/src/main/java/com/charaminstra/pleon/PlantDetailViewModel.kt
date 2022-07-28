package com.charaminstra.pleon

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.PlantIdRepository
import com.charaminstra.pleon.foundation.model.PlantDataObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantDetailViewModel @Inject constructor(private val repository: PlantIdRepository) : ViewModel() {
    private val TAG = javaClass.name

    private val _data = MutableLiveData<PlantDataObject>()
    val data: LiveData<PlantDataObject> = _data

    fun loadData(id: String){
        viewModelScope.launch {
            val data = repository.getPlantId(id)
            when (data.isSuccessful) {
                true -> {
                    _data.postValue(data.body()?.data!!)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }
}