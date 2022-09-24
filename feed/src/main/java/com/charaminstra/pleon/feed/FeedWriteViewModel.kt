package com.charaminstra.pleon.feed

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.common.ActionType
import com.charaminstra.pleon.foundation.FeedRepository
import com.charaminstra.pleon.foundation.ImageRepository
import com.charaminstra.pleon.foundation.PlantIdRepository
import com.charaminstra.pleon.foundation.PlantsRepository
import com.charaminstra.pleon.foundation.model.PlantDataObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class FeedWriteViewModel @Inject constructor(
    private val repository: FeedRepository,
    private val imageRepository: ImageRepository,
    private val plantRepository: PlantIdRepository,
    private val plantsRepository: PlantsRepository
): ViewModel() {
    private val TAG = javaClass.name
    private val _postSuccess = MutableLiveData<Boolean>()
    val postSuccess : LiveData<Boolean> = _postSuccess

    private val _urlResponse = MutableLiveData<String?>()
    val urlResponse : LiveData<String?> = _urlResponse

    private val _plantName = MutableLiveData<String>()
    val plantName : LiveData<String> = _plantName

    private val _plantsList = MutableLiveData<List<PlantDataObject>>()
    val plantsList : LiveData<List<PlantDataObject>> = _plantsList

    var plantId : String? = null
    var plantAction : ActionType? = null

    fun postFeed(date:String, content:String){
        Log.i(TAG,"\n plantId : "+plantId+"\n date: "+date+"\n kind : "+plantAction?.action!!+"\n content: "+content+"\n url: "+urlResponse.value)
        viewModelScope.launch {
            val data = repository.postFeed(plantId!!, date, plantAction?.action!!, content, urlResponse.value)
            Log.i(TAG, "data -> $data")
            when (data.isSuccessful) {
                true -> {
                    _postSuccess.postValue(data.body()?.success!!)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.errorBody())
                }
            }
        }
    }

    fun getPlantList(){
        viewModelScope.launch {
            val data = plantsRepository.getPlants()
            Log.i(TAG, "data -> $data")
            Log.i(TAG, "data.body -> "+data.body())
            when (data.isSuccessful) {
                true -> {
                    _plantsList.postValue(data.body()?.data!!)
                    plantId = data.body()?.data?.get(0)?.id
                    _plantName.postValue(data.body()?.data?.get(0)?.name!!)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    fun getPlantName(){
        viewModelScope.launch {
            val data = plantRepository.getPlantId(plantId!!)
            Log.i(TAG,"data -> $data")
            when (data.isSuccessful) {
                true -> {
                    Log.i(TAG,"data.body -> "+data.body())
                    _plantName.postValue(data.body()?.data?.name!!)
                }
                else -> {
                    Log.i(TAG,"FAIL-> ")
                }
            }
        }
    }

    fun cameraToUrl(inputStream: InputStream){
        viewModelScope.launch {
            val data =imageRepository.postImage(inputStream)
            Log.i(TAG,"data -> $data")
            when (data.isSuccessful) {
                true -> {
                    Log.i(TAG,"data.body -> "+data.body())
                    _urlResponse.postValue(data.body()?.data?.url)
                }
                else -> {
                    Log.i(TAG,"FAIL-> ")
                }
            }
        }
    }
    fun galleryToUrl(bitmap: Bitmap){
        viewModelScope.launch {
            ByteArrayOutputStream().use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val inputStream = ByteArrayInputStream(stream.toByteArray())
                val data = imageRepository.postImage(inputStream)
                Log.i(TAG,"data -> $data")
                when (data.isSuccessful) {
                    true -> {
                        Log.i(TAG,"data.body -> "+data.body())
                        _urlResponse.postValue(data.body()?.data?.url)
                    }
                    else -> {
                        Log.i(TAG,"FAIL-> ")
                    }
                }
            }
        }
    }
}