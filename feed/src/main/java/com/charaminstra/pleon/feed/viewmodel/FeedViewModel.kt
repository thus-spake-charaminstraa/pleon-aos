package com.charaminstra.pleon.feed.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.common.FeedRepository
import com.charaminstra.pleon.foundation.NotiRepository
import com.charaminstra.pleon.foundation.model.GuideViewTypeData
import com.charaminstra.pleon.common.ResultObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val notiRepository: NotiRepository
) : ViewModel() {
    private val TAG = javaClass.name

    private val _feedAllList = MutableLiveData<ArrayList<ResultObject>>()
    val feedAllList : LiveData<ArrayList<ResultObject>> = _feedAllList

    private val _feedCount = MutableLiveData<Int>()
    val feedCount : LiveData<Int> = _feedCount

    private val _feedFilterList = MutableLiveData<ArrayList<ResultObject>>()
    val feedFilterList : LiveData<ArrayList<ResultObject>> = _feedFilterList

    private val _notiList = MutableLiveData<List<GuideViewTypeData>>()
    val notiList : LiveData<List<GuideViewTypeData>> = _notiList

    private val _isLast = MutableLiveData<Boolean>()
    val isLast : LiveData<Boolean> = _isLast

    private val _notiCompleteSuccess = MutableLiveData<Boolean>()
    val notiCompleteSuccess : LiveData<Boolean> = _notiCompleteSuccess

    private val _notiDialogIsExist = MutableLiveData<Boolean>()
    val notiDialogIsExist : LiveData<Boolean> = _notiDialogIsExist

    private val _notiNew = MutableLiveData<Boolean>()
    val notiNew : LiveData<Boolean> = _notiNew

    var notiDialogTitle : String? = null
    var notiDialogContent : String? = null
    var notiDialogButton : Boolean?= null

    var offset = 0
    var plantId: String? = null

    fun getFeedAllList(){
        viewModelScope.launch {
            val data = feedRepository.getFeed(offset, plantId, null)
            when (data.isSuccessful) {
                true -> {
                    _feedCount.postValue(data.body()?.data?.result?.size)
                    _isLast.postValue(data.body()?.data?.isLast)
                    _feedAllList.postValue(data.body()?.data?.result)
                    offset = data.body()?.data?.next_offset!!
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

//    fun getFeedFilterList(){
//        viewModelScope.launch {
//            val data = feedRepository.getFeed(offset, plantId, null)
//            when (data.isSuccessful) {
//                true -> {
//                    _feedFilterList.postValue(data.body()?.data?.result)
//                    offset = data.body()?.data?.next_offset!!
//                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
//                }
//                else -> {
//                    Log.i(TAG,"FAIL -> "+ data.body().toString())
//                }
//            }
//
//        }
//    }

    fun getNotiList(){
        viewModelScope.launch {
            val data = notiRepository.getGuideList()
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

    fun clearFeedSetting(){
        offset = 0
        plantId = null
    }

    fun postNotiClick(notiId: String, type: String){
        viewModelScope.launch {
            val data = notiRepository.postNotiAction(notiId, type)
            Log.i(TAG,"postnoticlick -> "+ notiId+"\n"+type)
            Log.i(TAG,"postnoticlick -> "+ data.body().toString())
            when (data.isSuccessful) {
                true -> {
                    getNotiList()
                    clearFeedSetting()
                    getFeedAllList()
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    fun getNotiDialog(){
        viewModelScope.launch {
            val data = notiRepository.getNotiDialog()
            when (data.isSuccessful) {
                true -> {
                    var isExist = data.body()?.data?.isExist
                    _notiDialogIsExist.postValue(isExist)
                    if(isExist == true){
                        notiDialogButton = data.body()?.data?.notices?.get(0)?.button
                        notiDialogTitle = data.body()?.data?.notices?.get(0)?.title
                        notiDialogContent = data.body()?.data?.notices?.get(0)?.content
                    }
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    fun postNotiTodayStop(){
        viewModelScope.launch {
            val data = notiRepository.postNotiTodayStop()
            when (data.isSuccessful) {
                true -> {
                    _notiDialogIsExist.postValue(false)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    fun getNotiNew(){
        viewModelScope.launch {
            val data = notiRepository.getNotiNew()
            when (data.isSuccessful) {
                true -> {
                    _notiNew.postValue(data.body()?.data)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }
}