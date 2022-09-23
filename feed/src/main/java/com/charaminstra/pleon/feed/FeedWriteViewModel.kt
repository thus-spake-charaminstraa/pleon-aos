package com.charaminstra.pleon.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.common.ActionType
import com.charaminstra.pleon.foundation.FeedRepository
import com.charaminstra.pleon.foundation.ImageRepository
import com.charaminstra.pleon.foundation.PlantIdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class FeedWriteViewModel @Inject constructor(
    private val repository: FeedRepository,
    private val imageRepository: ImageRepository,
    private val plantRepository: PlantIdRepository
): ViewModel() {
    private val TAG = javaClass.name
    private val _postSuccess = MutableLiveData<Boolean>()
    val postSuccess : LiveData<Boolean> = _postSuccess

    private val _urlResponse = MutableLiveData<String?>()
    val urlResponse : LiveData<String?> = _urlResponse

    private val _plantName = MutableLiveData<String>()
    val plantName : LiveData<String> = _plantName

    var plantId : String? = null
    var plantAction : ActionType? = null

    private val _actionPosition = MutableLiveData<Int>()
    val actionPosition : LiveData<Int> = _actionPosition

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

    fun postImage(stream: InputStream){
        viewModelScope.launch {
            //val data =repository.postImage(uri, realPathFromURI!!)
            val data =imageRepository.postImage(stream)
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

    fun setActionPostion(position: Int){
        viewModelScope.launch {
            _actionPosition.postValue(position)
        }
    }
}