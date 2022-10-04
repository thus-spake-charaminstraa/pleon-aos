package com.charaminstra.pleon.doctor

import android.util.Log
import androidx.lifecycle.*
import com.charaminstra.pleon.foundation.ImageRepository
import com.charaminstra.pleon.foundation.InferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(private val imageRepository: ImageRepository,
                                          private val inferenceRepository: InferenceRepository)
    :ViewModel(){

    private val TAG = javaClass.name

    private val _firstImgUrlResponse = MutableLiveData<String?>()
    val firstImgUrlResponse : LiveData<String?> = _firstImgUrlResponse

    private val _secondImgUrlResponse = MutableLiveData<String?>()
    val secondImgUrlResponse : LiveData<String?> = _secondImgUrlResponse

    private val _plantDoctorSuccess = MutableLiveData<Boolean?>()
    val plantDoctorSuccess : LiveData<Boolean?> = _plantDoctorSuccess

    fun firstImgToUrl(){

    }

    fun secondImgToUrl(){

    }

    fun postPlantDoctorModel(){
        viewModelScope.launch {
            val data = inferenceRepository.postPlantDetection(firstImgUrlResponse.value.toString())
            Log.i(TAG,"plant detection data -> $data")
            when(data.isSuccessful){
                true -> {
                    Log.i(TAG,"plant detection data ->"+data.body().toString())
                    if(data.body()?.success == false){
                        _plantDoctorSuccess.postValue(false)
                    }else{
                        _plantDoctorSuccess.postValue(true)
//                        _plantDetectionResultLabel.postValue(data.body()?.species?.name)
//                        _plantDetectionResultPercent.postValue(data.body()?.score!!)
                    }
                }else -> {
                Log.i(TAG,"plant detection error")
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