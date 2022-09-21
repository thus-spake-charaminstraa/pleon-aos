package com.charaminstra.pleon.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.FeedRepository
import com.charaminstra.pleon.foundation.NotiRepository
import com.charaminstra.pleon.foundation.model.NotiData
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

    private val _feedList = MutableLiveData<List<ResultObject>>()
    val feedList : LiveData<List<ResultObject>> = _feedList

    private val _notiList = MutableLiveData<List<NotiData>>()
    val notiList : LiveData<List<NotiData>> = _notiList

    private val _feedCount = MutableLiveData<Int>()
    val feedCount : LiveData<Int> = _feedCount

//    private val _notiClickSuccess = MutableLiveData<Boolean>()
//    val notiClickSuccess : LiveData<Boolean> = _notiClickSuccess

//    var offset: Int = 0

    fun getFeedList(plantId: String?){
        viewModelScope.launch {
            val data = feedRepository.getOnlyFeed2(null, plantId)
            //val data = feedRepository.getFeedList(plant_Id)
            when (data.isSuccessful) {
                true -> {
                    _feedList.postValue(data.body()?.data?.result)
                    _feedCount.postValue(data.body()?.data?.result?.size)
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
            when (data.isSuccessful) {
                true -> {
                    getFeedList(null)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }
}