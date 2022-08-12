package com.charaminstra.pleon.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.FeedRepository
import com.charaminstra.pleon.foundation.model.FeedObject
import com.charaminstra.pleon.foundation.model.ResultObject
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

    private val _feedTabList = MutableLiveData<List<ResultObject>>()
    val feedTabList : LiveData<List<ResultObject>> = _feedTabList

    var offset: Int = 0

    fun loadData(plantId: String?, date: String?){
        viewModelScope.launch {
            val data = repository.getFeedList(offset, plantId, date)
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

    fun feedTabList(){
        viewModelScope.launch {
            val data = repository.getFeedTabList()
            Log.i(TAG, "feed tab list data -> $data")
            Log.i(TAG, "feed tab list data.body -> "+data.body())
            when (data.isSuccessful) {
                true -> {
                    _feedTabList.postValue(data.body()?.data?.result)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }

        }
    }





}