package com.charaminstra.pleon

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.model.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: com.charaminstra.pleon.AuthRepository) : ViewModel() {
    val authResponse = MutableLiveData<AuthResponse>()

    fun getData() = authResponse

    init {
        getAuth()
    }

    fun getAuth(){
        viewModelScope.launch {
            val data = repository.getAuth()
            when (data.isSuccessful) {
                true -> {
                    authResponse.postValue(data.body())
                    Log.i(ContentValues.TAG,"SUCCESS -> $data")
                }
                else -> {
                    Log.i(ContentValues.TAG,"FAIL -> $data")
                }
            }
        }
    }
}