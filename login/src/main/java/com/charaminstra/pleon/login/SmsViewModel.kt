package com.charaminstra.pleon.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.model.DataObject
import com.charaminstra.pleon.foundation.model.SmsResponse
import com.charaminstra.pleon.foundation.model.TokenObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PHONE_TAG = "sms view model : phone"
const val CODE_TAG = "sms view model : code"
const val LOGIN_TAG = "sms view model : login"

@HiltViewModel
class SmsViewModel @Inject constructor(private val repository: SmsRepository) : ViewModel() {

    var phoneResponse = MutableLiveData<SmsResponse>()
    var liveData = MutableLiveData<DataObject?>()
    val tokenResponse = MutableLiveData<TokenObject>()

    fun postPhoneNum(phone: String){
        viewModelScope.launch {
            val data =repository.postPhoneNum(phone)
            when (data.isSuccessful) {
                true -> {
                    phoneResponse.postValue(data.body())
                    Log.i(PHONE_TAG,"SUCCESS -> $data")
                }
                else -> {
                    Log.i(PHONE_TAG,"FAIL -> $data")
                }
            }
        }
    }
    fun postCode(phone: String, code:String){
        viewModelScope.launch {
            val data = repository.postCode(phone,code)
            when (data.isSuccessful) {
                true -> {
                    liveData.postValue(data.body()?.data)
                    Log.i(CODE_TAG,"SUCCESS -> $data"+"\n"+data.body())
                }
                else -> {
                    Log.i(CODE_TAG,"FAIL -> $data"+"\n"+data.errorBody())
                }
            }
        }
    }

    fun login(){
        viewModelScope.launch {
            val data = repository.postLogin()
            when (data.isSuccessful) {
                true -> {
                    tokenResponse.postValue(data.body())
                    Log.i(LOGIN_TAG,"SUCCESS -> $data")
                }
                else -> {
                    Log.i(LOGIN_TAG,"FAIL -> $data")
                }
            }
        }
    }
}