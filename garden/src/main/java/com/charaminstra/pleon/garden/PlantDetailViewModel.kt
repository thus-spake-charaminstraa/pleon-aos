package com.charaminstra.pleon.garden

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.FeedRepository
import com.charaminstra.pleon.foundation.PlantIdRepository
import com.charaminstra.pleon.foundation.ScheduleRepository
import com.charaminstra.pleon.foundation.model.FeedObject
import com.charaminstra.pleon.foundation.model.PlantDataObject
import com.charaminstra.pleon.foundation.model.ResultObject
import com.charaminstra.pleon.foundation.model.ScheduleDataObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
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

    private val _feedList = MutableLiveData<List<ResultObject>>()
    val feedList : LiveData<List<ResultObject>> = _feedList

    private val _patchSuccess = MutableLiveData<Boolean>()
    val patchSuccess: LiveData<Boolean> = _patchSuccess

    private val _deleteSuccess = MutableLiveData<Boolean>()
    val deleteSuccess: LiveData<Boolean> = _deleteSuccess

    val name = MutableLiveData<String>()
    val species = MutableLiveData<String>()
    val water_date = MutableLiveData<String>()
    val adopt_date = MutableLiveData<String>()
    val light = MutableLiveData<String>()
    val air = MutableLiveData<String>()
    //val thumbnail = MutableLiveData<String>()
    private val _urlResponse = MutableLiveData<String?>()
    val urlResponse : LiveData<String?> = _urlResponse

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

    var plantId: String? = null
    var offset: Int = 0

    fun getPlantData(id: String){
        viewModelScope.launch {
            val data = repository.getPlantId(id)
            when (data.isSuccessful) {
                true -> {
                    _plantData.postValue(data.body()?.data!!)
//                    setUrl(data.body()?.data?.thumbnail!!)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    fun getFeed(date: String?){
        viewModelScope.launch {
            val data = feedRepository.getOnlyFeed(offset, plantId, date)
            Log.i(TAG, "data -> $data")
            Log.i(TAG, "data.body -> "+data.body())
            when (data.isSuccessful) {
                true -> {
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
            Log.i(TAG, plantId!!)
            val data = scheduleRepository.getSchedule(plantId!!,year,month)
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

    fun patchData(id: String,
                  name: String,
                  adopt_date: String){
        viewModelScope.launch {
            val data = repository.patchPlantId(id,name,adopt_date,
                urlResponse.value.toString(),
                getLight().value.toString(),
                getAir().value.toString())
            Log.i(TAG, "patch DATA"+data.body())
            when (data.isSuccessful) {
                true -> {
                    _patchSuccess.postValue(data.body()?.success)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    fun deleteData(id: String){
        viewModelScope.launch {
            val data = repository.deletePlantId(id)
            Log.i(TAG, "delete DATA"+data.body())
            when (data.isSuccessful) {
                true -> {
                    _deleteSuccess.postValue(data.body()?.success)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }
}