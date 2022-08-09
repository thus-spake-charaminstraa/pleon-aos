package com.charaminstra.pleon.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.FeedRepository
import com.charaminstra.pleon.foundation.model.FeedObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: FeedRepository
) : ViewModel() {
    private val TAG = javaClass.name
    private val _feedList = MutableLiveData<List<FeedObject>>()
    val feedList : LiveData<List<FeedObject>> = _feedList

    private val _feedDeleteSuccess = MutableLiveData<Boolean?>()
    val feedDeleteSuccess : LiveData<Boolean?> = _feedDeleteSuccess

    var offset: Int = 0

    fun loadData(plantId: String?, date: String?){
        viewModelScope.launch {
            val data = repository.getFeed(offset, plantId, date)
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



    fun deleteFeed(feedId: String){
        viewModelScope.launch {
            val data = repository.deleteFeed(feedId)
            when (data.isSuccessful) {
                true -> {
                    _feedDeleteSuccess.postValue(data.body()?.success)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

}