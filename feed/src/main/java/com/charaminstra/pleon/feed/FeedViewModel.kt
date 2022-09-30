package com.charaminstra.pleon.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.FeedRepository
import com.charaminstra.pleon.foundation.NotiRepository
import com.charaminstra.pleon.foundation.model.NotiData
import com.charaminstra.pleon.foundation.model.NotiViewTypeData
import com.charaminstra.pleon.foundation.model.ResultObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val notiRepository: NotiRepository
) : ViewModel() {
    private val TAG = javaClass.name

    private val _feedAllList = MutableLiveData<List<ResultObject>>()
    val feedAllList : LiveData<List<ResultObject>> = _feedAllList

    private val _feedFilterList = MutableLiveData<List<ResultObject>>()
    val feedFilterList : LiveData<List<ResultObject>> = _feedFilterList

    private val _notiList = MutableLiveData<List<NotiViewTypeData>>()
    val notiList : LiveData<List<NotiViewTypeData>> = _notiList

    fun getFeedAllList(){
        viewModelScope.launch {
            val data = feedRepository.getOnlyFeed2(null, null)
            when (data.isSuccessful) {
                true -> {
                    _feedAllList.postValue(data.body()?.data?.result)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }

        }
    }

    fun getFeedFilterList(plantId: String?){
        viewModelScope.launch {
            val data = feedRepository.getOnlyFeed2(null, plantId)
            //val data = feedRepository.getFeedList(plant_Id)
            when (data.isSuccessful) {
                true -> {
                    _feedFilterList.postValue(data.body()?.data?.result)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }

        }
    }

    fun getNotiList(){
        viewModelScope.launch {
            val data = notiRepository.getNotiList()
            when(data.isSuccessful){
                true -> {
                    _notiList.postValue(data.body()?.data)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    fun postNotiClick(notiId: String, type: String){
        viewModelScope.launch {
            val data = notiRepository.postNotiAction(notiId, type)
            Log.i(TAG,"postnoticlick -> "+ notiId+"\n"+type)
            Log.i(TAG,"postnoticlick -> "+ data.body().toString())
            when (data.isSuccessful) {
                true -> {
                    getNotiList()
                    getFeedAllList()
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }
}