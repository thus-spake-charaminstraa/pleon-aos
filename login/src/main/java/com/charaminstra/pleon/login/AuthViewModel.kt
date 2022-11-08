package com.charaminstra.pleon.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.AuthRepository
import com.charaminstra.pleon.foundation.UserRepository
import com.charaminstra.pleon.foundation.api.PleonPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PHONE_TAG = "sms view model : phone"
const val CODE_TAG = "sms view model : code"
const val LOGIN_TAG = "sms view model : login"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val userRepository: UserRepository,
    private val prefs: PleonPreference) : ViewModel() {

    private val TAG = javaClass.name

    private var _phoneResponse = MutableLiveData<Boolean>()
    val phoneResponse : LiveData<Boolean> = _phoneResponse

    private var _success = MutableLiveData<Boolean>()
    val success : LiveData<Boolean> = _success

    private var _userExist = MutableLiveData<Boolean>()
    val userExist : LiveData<Boolean> = _userExist

    private var _setTokenSuccess = MutableLiveData<Boolean>()
    val setTokenSuccess : LiveData<Boolean> = _setTokenSuccess

    var kakaoName: String?= null

    private val phoneNum = MutableLiveData<String>()
    fun setPhoneNum(phone: String){
        phoneNum.value = phone
    }
    fun getPhoneNum(): LiveData<String>{
        return phoneNum
    }

    fun postPhoneNum(){
        viewModelScope.launch {
            /*test account*/
           // _phoneResponse.postValue(true)
            Log.i("phone num",getPhoneNum().value.toString())
            val data =repository.postPhoneNum(getPhoneNum().value.toString())
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
    fun postCode(code:String){
        viewModelScope.launch {
            val data = repository.postCode(getPhoneNum().value.toString(),code)
            Log.i(CODE_TAG,"data -> $data"+"\n"+data)
            when(data.isSuccessful){
                true -> {
                    _success.postValue(data.body()?.success)
                    prefs.setVerifyToken(data.body()?.data?.verify_token)
                    _userExist.postValue(data.body()?.data?.isExist!!)
                }
                else -> {
                    _success.postValue(false)
                    Log.i(CODE_TAG,"FAIL-> ")
                }
            }
        }
    }

    fun postKakaoToken(token:String){
        viewModelScope.launch {
            val data = repository.postKakaoToken(token)
            Log.i("kakaotoken","data -> $data"+"\n")
            when(data.isSuccessful){
                true -> {
                    _success.postValue(data.body()?.success)
                    prefs.setVerifyToken(data.body()?.data?.verify_token)
                    _userExist.postValue(data.body()?.data?.isExist!!)
                    postDeviceToken()
                }
                else -> {
                    _success.postValue(false)
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
                    _setTokenSuccess.postValue(true)
                    postDeviceToken()
                }
                else -> {
                    _setTokenSuccess.postValue(false)
                    Log.i(LOGIN_TAG,"FAIL -> $data")
                }
            }
        }
    }

    private fun postDeviceToken(){
        viewModelScope.launch {
            val data = userRepository.postDeviceToken()
            Log.i(TAG,"post device token -> "+data.body())
            when (data.isSuccessful) {
                true -> {
                    Log.i(TAG,"SUCCESS -> $data")
                }
                else -> {
                    Log.i(TAG,"FAIL -> $data")
                }
            }
        }
    }

}