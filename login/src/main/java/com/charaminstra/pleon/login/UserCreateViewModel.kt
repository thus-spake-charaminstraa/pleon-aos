package com.charaminstra.pleon.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.model.UserCreateDataObejct
import com.charaminstra.pleon.foundation.model.UserCreateResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserCreateViewModel @Inject constructor(private val repository: UserCreateRepository) : ViewModel() {
    private val TAG = javaClass.simpleName
    val userCreateResponse = MutableLiveData<UserCreateDataObejct>()
    fun userCreate(name: String){
        viewModelScope.launch {
            val data = repository.postNickname(name)
            Log.i(TAG,"data -> $data")
            when (data.isSuccessful) {
                true -> {
                    userCreateResponse.postValue(data.body()?.data!!)
                    Log.i(TAG,"SUCCESS -> $data")
                }
                else -> {
                    Log.i(TAG,"FAIL -> $data")
                }
            }
        }
    }

}