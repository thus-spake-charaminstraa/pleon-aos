package com.charaminstra.pleon.feed.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.NotiRepository
import com.charaminstra.pleon.foundation.model.NotiViewTypeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotiViewModel @Inject constructor(
    private val notiRepository: NotiRepository
) : ViewModel() {
    private val TAG = javaClass.name

    private val _notiList = MutableLiveData<List<NotiViewTypeData>>()
    val notiList : LiveData<List<NotiViewTypeData>> = _notiList

    init {
        getNotiList()
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
}