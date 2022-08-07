package com.charaminstra.pleon.plant_register

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel()  {
    private val TAG = javaClass.simpleName
    private val _urlResponse = MutableLiveData<String>()
    val urlResponse : LiveData<String> = _urlResponse

    fun getUrl() = urlResponse

    fun postImage(stream: InputStream){
        viewModelScope.launch {
            //val data =repository.postImage(uri, realPathFromURI!!)
            val data =repository.postImage(stream)
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