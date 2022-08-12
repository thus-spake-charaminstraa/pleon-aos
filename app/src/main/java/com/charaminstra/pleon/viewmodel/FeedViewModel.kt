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

    private val _feedList = MutableLiveData<List<ResultObject>>()
    val feedList : LiveData<List<ResultObject>> = _feedList

    fun getFeedList(){
        viewModelScope.launch {
            val data = repository.getFeedList()
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
}