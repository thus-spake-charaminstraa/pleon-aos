package com.charaminstra.pleon.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.ScheduleRepository
import com.charaminstra.pleon.foundation.model.FeedObject
import com.charaminstra.pleon.foundation.model.ScheduleDataObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
): ViewModel() {
    private val TAG = javaClass.name

    private val _scheduleData = MutableLiveData<List<ScheduleDataObject>>()
    val scheduleData : LiveData<List<ScheduleDataObject>> = _scheduleData

    var plantId: String? = null

    fun getSchedule(year:Int, month: Int) {
        viewModelScope.launch {
            val data = scheduleRepository.getSchedule(
                plantId!!,year,month)
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