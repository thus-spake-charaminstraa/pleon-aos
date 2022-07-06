package com.charaminstra.pleon.login

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(private val repository: TestRepository) : ViewModel() {


    var liveData = MutableLiveData<String>("응답 없음")

    fun getData() = liveData

    init {
        loadData()
//        login("","")
    }

    fun loadData() {
        viewModelScope.launch {
            val data = repository.postSignIn("01011112222","777777")
            Log.i(TAG, "loadData: $data")
            when (data.isSuccessful) {
                true -> {
                    liveData.postValue(data.body().toString())
                }
                else -> {
                    Log.i(TAG,"TEST -> ${data.body()}")
                }
            }
        }
    }
//    fun login(phone: String, code:String){
//        Log.i(TAG, "loadData: $phone $code")
//        viewModelScope.launch {
//
//            val data = repository.postSignIn(phone,code)
//            Log.i(TAG, "loadData: $data")
//            when (data.isSuccessful) {
//                true -> {
//                    liveData.postValue(data.body().toString())
//                }
//                else -> {
//                    Log.i(TAG,"TEST -> ${data.body()}")
//                }
//            }
//        }
//    }

}