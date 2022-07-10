package com.charaminstra.pleon.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.model.DataObject
import com.charaminstra.pleon.foundation.model.SmsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmsViewModel @Inject constructor(private val repository: SmsRepository) : ViewModel() {
    var phoneResponse = MutableLiveData<SmsResponse>()
    var liveData = MutableLiveData<DataObject?>()

    fun postPhoneNum(phone: String){
//        viewModelScope.launch {
//            val data =repository.postPhoneNum(phone)
//            when (data.isSuccessful) {
//                true -> {
//                    phoneResponse.postValue(data.body())
//                    Log.i(TAG,"SUCCESS -> $data")
//                }
//                else -> {
//                    Log.i(TAG,"FAIL -> $data")
//                }
//            }
//        }
    }
    fun postCode(phone: String, code:String){
//        viewModelScope.launch {
//            val data = repository.postCode(phone,code)
//            when (data.isSuccessful) {
//                true -> {
//                    liveData.postValue(data.body()?.data)
//                    Log.i(TAG,"SUCCESS -> $data")
//                }
//                else -> {
//                    Log.i(TAG,"FAIL -> $data")
//                }
//            }
//        }
    }
}