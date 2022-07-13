package com.charaminstra.pleon.login

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.model.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    private val authResponse = MutableLiveData<Response<AuthResponse>>()

    fun getData() = authResponse

    init {
        loadAuth()
    }

    fun loadAuth(){
        viewModelScope.launch {
            delay(1000)
            val data = repository.getAuth()
            when (data.isSuccessful) {
                true -> {
                    authResponse.postValue(data)
                    Log.i(TAG,"SUCCESS -> "+ data.toString())
                }
                else -> {
                    authResponse.postValue(data)
                    Log.i(TAG,"FAIL -> "+ data.toString())
                }
            }
        }
    }
}