package com.charaminstra.pleon.doctor

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import com.charaminstra.pleon.foundation.ImageRepository
import com.charaminstra.pleon.foundation.InferenceRepository
import com.charaminstra.pleon.foundation.model.CauseObject
import com.charaminstra.pleon.foundation.model.SymptomObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(private val imageRepository: ImageRepository,
                                          private val inferenceRepository: InferenceRepository)
    :ViewModel(){

    private val TAG = javaClass.name

    var currentIdx = 1

    private val _firstImgUrlResponse = MutableLiveData<String?>()
    val firstImgUrlResponse : LiveData<String?> = _firstImgUrlResponse

    private val _secondImgUrlResponse = MutableLiveData<String?>()
    val secondImgUrlResponse : LiveData<String?> = _secondImgUrlResponse

    private val _plantDoctorSuccess = MutableLiveData<Boolean?>()
    val plantDoctorSuccess : LiveData<Boolean?> = _plantDoctorSuccess

    private val _symptomsList = MutableLiveData<List<SymptomObject>>()
    val symptomsList : LiveData<List<SymptomObject>> = _symptomsList

    private val _causesList = MutableLiveData<List<CauseObject>>()
    val causesList : LiveData<List<CauseObject>> = _causesList

    fun imgToUrl(inputStream: InputStream){
        viewModelScope.launch {
            val data =imageRepository.postImage(inputStream)
            Log.i(TAG,"data -> $data")
            when (data.isSuccessful) {
                true -> {
                    Log.i(TAG,"data.body -> "+data.body())
                    if(currentIdx == 1){
                        _firstImgUrlResponse.value = data.body()?.data?.url
                        currentIdx += 1
                    }else if(currentIdx == 2){
                        _secondImgUrlResponse.value = data.body()?.data?.url
                    }

                }
                else -> {
                    Log.i(TAG,"FAIL-> ")
                }
            }
        }
    }
    fun postPlantDoctorModel(){
        viewModelScope.launch {
            val data = inferenceRepository.postPlantDoctor(firstImgUrlResponse.value.toString(),secondImgUrlResponse.value.toString())
            Log.i(TAG,"plant doctor data -> $data")
            when(data.isSuccessful){
                true -> {
                    Log.i(TAG,"plant doctor data ->"+data.body().toString())
                    if(data.body()?.success == false){
                        _plantDoctorSuccess.postValue(false)
                    }else{
                        _plantDoctorSuccess.postValue(true)
                        _symptomsList.postValue(data.body()?.data?.symptoms)
                        _causesList.postValue(data.body()?.data?.causes)
                    }
                }else -> {
                    Log.i(TAG,"plant doctor error")
            }
            }
        }
    }

    fun warmingPlantDoctorModel(){
        viewModelScope.launch {
            inferenceRepository.warmingPlantDoctor()
        }
    }
}