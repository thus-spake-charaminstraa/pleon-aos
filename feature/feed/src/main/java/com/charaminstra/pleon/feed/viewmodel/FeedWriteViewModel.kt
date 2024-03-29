package com.charaminstra.pleon.feed.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.common.model.ActionData
import com.charaminstra.pleon.common.repository.FeedRepository
import com.charaminstra.pleon.common.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class FeedWriteViewModel @Inject constructor(
    private val repository: FeedRepository,
    private val imageRepository: ImageRepository,
): ViewModel() {
    private val TAG = javaClass.name
    private val _postSuccess = MutableLiveData<Boolean>()
    val postSuccess : LiveData<Boolean> = _postSuccess

    private val _urlResponse = MutableLiveData<String?>()
    val urlResponse : LiveData<String?> = _urlResponse

    private val _actionList = MutableLiveData<List<ActionData>>()
    val actionList : LiveData<List<ActionData>> = _actionList

    var plantId : String? = null
    var plantAction : ActionData? = null
    var plantName : String? = null

    private val _imgBitmap = MutableLiveData<Bitmap?>()
    var imgBitmap : LiveData<Bitmap?> = _imgBitmap

    fun setBitmap(bitmap: Bitmap){
        viewModelScope.launch {
            _imgBitmap.value = bitmap
        }
    }

    fun clearBitmap(){
        _imgBitmap.value = null
    }

    fun imgBitmapToUrl(){
        viewModelScope.launch {
            ByteArrayOutputStream().use { stream ->
                imgBitmap.value?.compress(Bitmap.CompressFormat.JPEG, 70, stream)
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

    fun postFeed(date:String, content:String){
        Log.i(TAG,"\n plantId : "+plantId+"\n date: "+date+"\n kind : "+plantAction?.name_en!!+"\n content: "+content+"\n url: "+urlResponse.value)
        viewModelScope.launch {
            val data = repository.postFeed(plantId!!, date, plantAction?.name_en!!, content, urlResponse.value)
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

    fun getActionList(){
        viewModelScope.launch{
            val data = repository.getAction()
            Log.i(TAG,"action.data.body -> "+data.body())
            when(data.isSuccessful){
                true -> {
                    plantAction = data.body()?.data?.get(0)
                    _actionList.postValue(data.body()?.data!!)
                }
                else -> {}
            }
        }
    }
}