package com.charaminstra.pleon.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.PleonPreference
import com.charaminstra.pleon.foundation.model.UserCreateDataObejct
import com.charaminstra.pleon.foundation.model.UserCreateResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserCreateViewModel @Inject constructor(private val repository: UserCreateRepository, private val prefs: PleonPreference) : ViewModel() {
    private val TAG = javaClass.simpleName
    val userCreateResponse = MutableLiveData<UserCreateDataObejct>()
    fun userCreate(name: String){
        viewModelScope.launch {
            val data = repository.postNickname(name,prefs.getVerifyToken())
            Log.i(TAG,"data -> $data")
            when (data.isSuccessful) {
                true -> {
                    userCreateResponse.postValue(data.body()?.data!!)
                    Log.i(TAG,"SUCCESS -> $data")

                    prefs.setAccessToken(data.body()?.data?.token?.access_token)
                    prefs.setRefreshToken(data.body()?.data?.token?.refresh_token)
                    Log.d(TAG, "verify token"+prefs.getVerifyToken())
                    Log.d(TAG, "access token"+prefs.getAccessToken())
                    Log.d(TAG, "refresh token"+prefs.getRefreshToken())
                }
                else -> {
                    Log.i(TAG,"FAIL -> $data")
                }
            }
        }
    }

}