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
//    val userCreateResponse = MutableLiveData<UserCreateDataObejct>()
    val userCreateSuccess = MutableLiveData<Boolean?>()
    fun userCreate(name: String){
        viewModelScope.launch {
            val data = repository.postNickname(name,prefs.getVerifyToken())
            Log.i(TAG,"data -> $data")
            userCreateSuccess.postValue(data.body()?.success)
            when (data.body()?.success) {
                true -> {
                    //userCreateResponse.postValue(data.body()?.data!!)
                    prefs.setAccessToken(data.body()?.data?.token?.access_token)
                    prefs.setRefreshToken(data.body()?.data?.token?.refresh_token)
                }
                else -> {
                }
            }
        }
    }

}