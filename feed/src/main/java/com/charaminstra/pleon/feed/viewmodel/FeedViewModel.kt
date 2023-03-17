package com.charaminstra.pleon.feed.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.common.repository.FeedRepository
import com.charaminstra.pleon.common.repository.NotiRepository
import com.charaminstra.pleon.common.data.GuideViewTypeData
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

    private val _feedFilterList = MutableLiveData<ArrayList<ResultObject>>()
    val feedFilterList : LiveData<ArrayList<ResultObject>> = _feedFilterList

    private val _guideList = MutableLiveData<List<GuideViewTypeData>>()
    val guideList : LiveData<List<GuideViewTypeData>> = _guideList

    private val _isLast = MutableLiveData<Boolean>()
    val isLast : LiveData<Boolean> = _isLast

    private val _notiDialogIsExist = MutableLiveData<Boolean>()
    val notiDialogIsExist : LiveData<Boolean> = _notiDialogIsExist

    private val _hasNoti = MutableLiveData<Boolean>()
    val hasNoti : LiveData<Boolean> = _hasNoti

    var notiDialogTitle : String? = null
    var notiDialogContent : String? = null
    var notiDialogButton : Boolean?= null
    var notiImgUrl : String?= null

    var offset = 0
    var plantId: String? = null

    fun getFeedAllList(){
        viewModelScope.launch {
            val data = feedRepository.getFeed(offset, plantId, null)
            when (data.isSuccessful) {
                true -> {
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

    fun getGuideList(){
        viewModelScope.launch {
            val data = notiRepository.getGuideList()
            when(data.isSuccessful){
                true -> {
                    _guideList.postValue(data.body()?.data)
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

    fun postGuideClick(notiId: String, type: String){
        viewModelScope.launch {
            val data = notiRepository.postGuideAction(notiId, type)
            when (data.isSuccessful) {
                true -> {
                    getGuideList()
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

    fun getNotiExist(){
        viewModelScope.launch {
            val data = notiRepository.getNotiNew()
            when (data.isSuccessful) {
                true -> {
                    _hasNoti.postValue(data.body()?.data)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    //noti dialog
    fun getNotiDialog(){
        viewModelScope.launch {
            val data = notiRepository.getNotiDialog()
            when (data.isSuccessful) {
                true -> {
                    var isExist = data.body()?.data?.isExist
                    _notiDialogIsExist.postValue(isExist)
                    if(isExist == true){
                        notiImgUrl = data.body()?.data?.notices?.get(0)?.image_url
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

    fun postNotiDialogTodayStop(){
        viewModelScope.launch {
            val data = notiRepository.postNotiDialogTodayStop()
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
}