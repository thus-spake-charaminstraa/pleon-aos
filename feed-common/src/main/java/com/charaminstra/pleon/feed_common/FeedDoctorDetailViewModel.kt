package com.charaminstra.pleon.feed_common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.PlantIdRepository
import com.charaminstra.pleon.foundation.model.CauseObject
import com.charaminstra.pleon.foundation.model.SymptomObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FeedDoctorDetailViewModel @Inject constructor(
    private val plantRepository: PlantIdRepository
): ViewModel(){
    private val TAG = javaClass.name

    private val _symptomsList = MutableLiveData<List<SymptomObject>>()
    val symptomsList : LiveData<List<SymptomObject>> = _symptomsList

    private val _causesList = MutableLiveData<List<CauseObject>>()
    val causesList : LiveData<List<CauseObject>> = _causesList

    private val _date = MutableLiveData<Date?>()
    val date : LiveData<Date?> = _date

    private val _plantName = MutableLiveData<String?>()
    val plantName : LiveData<String?> = _plantName

    fun getDiagnosis(id: String){
        viewModelScope.launch {
            val data = plantRepository.getPlantDiagnosis(id)
            when(data.isSuccessful){
                true -> {
                    Log.i(TAG,"data ->"+data.body().toString())
                    if(data.body()?.success == false){

                    }else{
                        _date.postValue(data.body()?.data?.created_at)
                        _symptomsList.postValue(data.body()?.data?.symptoms)
                        _causesList.postValue(data.body()?.data?.causes)
                        _plantName.postValue(data.body()?.data?.plant?.name)
                    }
                }else -> {
                Log.i(TAG,"plant doctor error")
            }
            }
        }
    }
}