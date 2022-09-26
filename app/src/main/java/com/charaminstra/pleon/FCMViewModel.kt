package com.charaminstra.pleon

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class FCMViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val TAG = javaClass.name

    fun postDeviceToekn(token: String){
        viewModelScope.launch {
            val data = repository.postDeviceToken(token)
            Log.i(TAG, "data -> $data")
            when (data.isSuccessful) {
                true -> {
                    Log.i(TAG,"SUCCESS -> "+ data.toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.toString())
                }
            }
        }
    }
}