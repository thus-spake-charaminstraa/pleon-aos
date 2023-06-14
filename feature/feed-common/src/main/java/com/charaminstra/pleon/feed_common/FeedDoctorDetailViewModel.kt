package com.charaminstra.pleon.feed_common

import android.util.Log
import androidx.lifecycle.*
import com.charaminstra.pleon.common.model.CauseObject
import com.charaminstra.pleon.common.model.SymptomObject
import com.charaminstra.pleon.common.repository.PlantIdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FeedDoctorDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val plantRepository: PlantIdRepository
): ViewModel(){
    private val TAG = javaClass.name

    var feedId: String = savedStateHandle.get<String>("feedId")!!

    private val _symptomsList = MutableLiveData<List<SymptomObject>?>()
    val symptomsList : LiveData<List<SymptomObject>?> = _symptomsList

    private val _causesList = MutableLiveData<List<CauseObject>?>()
    val causesList : LiveData<List<CauseObject>?> = _causesList

    private val _date = MutableLiveData<Date?>()
    val date : LiveData<Date?> = _date

    private val _plantName = MutableLiveData<String?>()
    val plantName : LiveData<String?> = _plantName

    init {
        getDiagnosis()
    }

    fun getDiagnosis(){
        viewModelScope.launch {
            val data = plantRepository.getPlantDiagnosis(feedId!!)
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