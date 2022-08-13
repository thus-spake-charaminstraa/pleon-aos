package com.charaminstra.pleon.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.UserRepository
import com.charaminstra.pleon.foundation.api.PleonPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserCreateViewModel @Inject constructor(private val repository: UserRepository, private val prefs: PleonPreference) : ViewModel() {
    private val TAG = javaClass.simpleName
    private var _userCreateSuccess = MutableLiveData<Boolean?>()
    val userCreateSuccess : LiveData<Boolean?> = _userCreateSuccess
    fun userCreate(name: String){
        viewModelScope.launch {
            val data = repository.postNickname(name)
            Log.i(TAG,"data -> $data")
            _userCreateSuccess.postValue(data.body()?.success)
            when (data.body()?.success) {
                true -> {
                    //userCreateResponse.postValue(data.body()?.data!!)
                    prefs.setAccessToken(data.body()?.data?.token?.access_token)
                    prefs.setRefreshToken(data.body()?.data?.token?.refresh_token)
                    prefs.setName(data.body()?.data?.user?.nickname)
                }
                else -> {
                }
            }
        }
    }

}