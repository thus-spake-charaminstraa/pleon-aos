package com.charaminstra.pleon.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.PleonPreference
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
class SmsViewModel @Inject constructor(private val repository: SmsRepository, private val prefs: PleonPreference) : ViewModel() {

    //var phoneResponse = MutableLiveData<SmsResponse>()
    var phoneResponse = MutableLiveData<Boolean>()
    var codeResponse = MutableLiveData<Boolean>()
    var userExist = MutableLiveData<Boolean>()
    val tokenResponse = MutableLiveData<TokenObject>()

    fun postPhoneNum(phone: String){
        viewModelScope.launch {
            val data =repository.postPhoneNum(phone)
            Log.i(PHONE_TAG,"post phone num response -> $data")
            when (data.isSuccessful) {
                true -> {
                    phoneResponse.postValue(true)
//                    phoneResponse.postValue(data.body())
                }
                else -> {
                    phoneResponse.postValue(false)
                }
            }
        }
    }
    fun postCode(phone: String, code:String){
        viewModelScope.launch {
            val data = repository.postCode(phone,code)
            codeResponse.postValue(data.body()?.success)
            when (data.body()?.success) {
                true -> {
                    prefs.setVerifyToken(data.body()?.data?.verify_token)
                    if(data.body()!!.data?.isExist == true){
                        userExist.postValue(true)
                    }else{
                        userExist.postValue(false)
                    }
                }
                else -> {
                    Log.i(CODE_TAG,"FAIL -> $data"+"\n"+data.errorBody())
                }
            }
        }
    }

    fun postLogin(){
        viewModelScope.launch {
            val data = repository.postLogin(prefs.getVerifyToken())
            when (data.isSuccessful) {
                true -> {
                    tokenResponse.postValue(data.body())
                    Log.i(LOGIN_TAG,"SUCCESS -> $data")
                    prefs.setRefreshToken(data.body()?.refresh_token)
                    prefs.setAccessToken(data.body()?.access_token)
                }
                else -> {
                    Log.i(LOGIN_TAG,"FAIL -> $data")
                }
            }
        }
    }
}