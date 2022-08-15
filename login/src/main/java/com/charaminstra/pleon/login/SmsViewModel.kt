package com.charaminstra.pleon.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.api.PleonPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PHONE_TAG = "sms view model : phone"
const val CODE_TAG = "sms view model : code"
const val LOGIN_TAG = "sms view model : login"

@HiltViewModel
class SmsViewModel @Inject constructor(private val repository: SmsRepository, private val prefs: PleonPreference) : ViewModel() {

    private var _phoneResponse = MutableLiveData<Boolean>()
    val phoneResponse : LiveData<Boolean> = _phoneResponse

    private var _codeResponse = MutableLiveData<Boolean>()
    val codeResponse : LiveData<Boolean> = _codeResponse

    private var _userExist = MutableLiveData<Boolean>()
    val userExist : LiveData<Boolean> = _userExist


    fun postPhoneNum(phone: String){
        viewModelScope.launch {
            /*test account*/
           // _phoneResponse.postValue(true)
            val data =repository.postPhoneNum(phone)
            Log.i(PHONE_TAG,"post phone num response -> $data")
            when (data.isSuccessful) {
                true -> {
                    _phoneResponse.postValue(true)
                }
                else -> {
                    _phoneResponse.postValue(false)
                }
            }
        }
    }
    fun postCode(phone: String, code:String){
        viewModelScope.launch {
            val data = repository.postCode(phone,code)
            _codeResponse.postValue(data.body()?.success)
            when (data.body()?.success) {
                true -> {
                    prefs.setVerifyToken(data.body()?.data?.verify_token)
                    if(data.body()!!.data?.isExist == true){
                        _userExist.postValue(true)
                    }else{
                        _userExist.postValue(false)
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
            val data = repository.postLogin()
            Log.i(LOGIN_TAG,"post login response -> "+data.body())
            when (data.isSuccessful) {
                true -> {
                    prefs.setRefreshToken(data.body()?.data?.token?.refresh_token)
                    prefs.setAccessToken(data.body()?.data?.token?.access_token)
                    prefs.setName(data.body()?.data?.user?.nickname)
                }
                else -> {
                    Log.i(LOGIN_TAG,"FAIL -> $data")
                }
            }
        }
    }
}