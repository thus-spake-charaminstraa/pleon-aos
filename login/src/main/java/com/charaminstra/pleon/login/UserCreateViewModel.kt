package com.charaminstra.pleon.login

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.charaminstra.pleon.foundation.UserRepository
import com.charaminstra.pleon.foundation.api.PleonPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserCreateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: UserRepository,
    private val prefs: PleonPreference) : ViewModel() {

    var loginMethod: String = savedStateHandle.get<String>("loginMethod")!!

    private val TAG = javaClass.simpleName
    private var _userCreateSuccess = MutableLiveData<Boolean?>()
    val userCreateSuccess : LiveData<Boolean?> = _userCreateSuccess

    fun userCreate(name: String){
        viewModelScope.launch {
            if(loginMethod == "phone"){
                val data = repository.postPhoneNickname(name)
                Log.i(TAG,"data -> $data")
                when (data.body()?.success) {
                    true -> {
                        prefs.setAccessToken(data.body()?.data?.token?.access_token)
                        prefs.setRefreshToken(data.body()?.data?.token?.refresh_token)
                        _userCreateSuccess.postValue(data.body()?.success)
                        postDeviceToken()
                    }
                    else -> {}
                }
            }else if(loginMethod == "kakao"){
                val data = repository.postKakaoNickname(name)
                Log.i(TAG,"data -> $data")
                when (data.body()?.success) {
                    true -> {
                        prefs.setAccessToken(data.body()?.data?.token?.access_token)
                        prefs.setRefreshToken(data.body()?.data?.token?.refresh_token)
                        _userCreateSuccess.postValue(data.body()?.success)
                        postDeviceToken()
                    }
                    else -> {}
                }
            }
        }
    }

    fun postDeviceToken(){
        viewModelScope.launch {
            val data = repository.postDeviceToken()
            Log.i(ContentValues.TAG,"post device token -> "+data.body())
            when (data.isSuccessful) {
                true -> {
                    Log.i(ContentValues.TAG,"SUCCESS -> $data")
                }
                else -> {
                    Log.i(ContentValues.TAG,"FAIL -> $data")
                }
            }
        }
    }

}