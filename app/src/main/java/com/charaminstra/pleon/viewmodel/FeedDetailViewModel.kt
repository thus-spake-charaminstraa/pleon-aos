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
class FeedDetailViewModel @Inject constructor(
    private val repository: FeedRepository
): ViewModel() {
    private val TAG = javaClass.name

    private val _feedData = MutableLiveData<FeedObject>()
    val feedData : LiveData<FeedObject> = _feedData

    private val _feedDeleteSuccess = MutableLiveData<Boolean?>()
    val feedDeleteSuccess : LiveData<Boolean?> = _feedDeleteSuccess


    fun loadFeed(feedId: String){
        viewModelScope.launch {
            val data = repository.getFeedId(feedId)
            when (data.isSuccessful) {
                true -> {
                    _feedData.postValue(data.body()?.data!!)
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
            val data = repository.deleteFeedId(feedId)
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