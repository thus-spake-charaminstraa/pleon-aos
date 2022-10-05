package com.charaminstra.pleon.doctor

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import com.charaminstra.pleon.foundation.ImageRepository
import com.charaminstra.pleon.foundation.InferenceRepository
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

//    fun secondImgToUrl(){
//
//    }

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