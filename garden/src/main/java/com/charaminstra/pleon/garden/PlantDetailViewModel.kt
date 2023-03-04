package com.charaminstra.pleon.garden

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.common.repository.FeedRepository
import com.charaminstra.pleon.foundation.PlantIdRepository
import com.charaminstra.pleon.foundation.ScheduleRepository
import com.charaminstra.pleon.foundation.model.PlantDataObject
import com.charaminstra.pleon.common.ResultObject
import com.charaminstra.pleon.foundation.model.ScheduleDataObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    private val repository: PlantIdRepository,
    private val scheduleRepository: ScheduleRepository,
    private val feedRepository: FeedRepository
): ViewModel() {
    private val TAG = javaClass.name

    private val _plantData = MutableLiveData<PlantDataObject>()
    val plantData: LiveData<PlantDataObject> = _plantData

    private val _scheduleData = MutableLiveData<List<ScheduleDataObject>>()
    val scheduleData : LiveData<List<ScheduleDataObject>> = _scheduleData

    private val _feedList = MutableLiveData<ArrayList<ResultObject>>()
    val feedList : LiveData<ArrayList<ResultObject>> = _feedList

    private val _plantId = MutableLiveData<String>()
    val plantId : LiveData<String> = _plantId

    var offset = 0

    fun setPlantId(id: String){
         viewModelScope.launch {
             _plantId.value = id
         }
    }

    fun getPlantData(){
        viewModelScope.launch {
            val data = repository.getPlantId(plantId.value.toString())
            when (data.isSuccessful) {
                true -> {
                    _plantData.postValue(data.body()?.data!!)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    fun offsetClear(){
        offset = 0
    }

    fun getFeed(date: String?){
        viewModelScope.launch {
            val data = feedRepository.getFeed(offset, plantId.value.toString(), date)
            Log.i(TAG, "data -> $data")
            Log.i(TAG, "data.body -> "+data.body())
            when (data.isSuccessful) {
                true -> {
                    offset = data.body()?.data?.next_offset!!
                    _feedList.postValue(data.body()?.data?.result)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    fun getSchedule(year:Int, month: Int) {
        viewModelScope.launch {
            val data = scheduleRepository.getSchedule(plantId.value.toString(),year,month)
            when(data.isSuccessful){
                true -> {
                    _scheduleData.postValue(data.body()?.data)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }

    }



}