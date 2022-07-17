package com.charaminstra.pleon.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.PleonPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository) : ViewModel() {

    private val TAG = javaClass.name
    private val authSuccess = MutableLiveData<Boolean>()
    fun getData() = authSuccess

    init {
        loadAuth()
    }

    fun loadAuth(){
        viewModelScope.launch {
            delay(1000)
            val data = repository.getAuth()
            Log.i(TAG, "data -> $data")
            when (data.isSuccessful) {
                true -> {
                    authSuccess.postValue(true)
                    Log.i(TAG,"SUCCESS -> "+ data.toString())
                }
                else -> {
                    authSuccess.postValue(false)
                    Log.i(TAG,"FAIL -> "+ data.toString())
                }
            }
        }
    }
}