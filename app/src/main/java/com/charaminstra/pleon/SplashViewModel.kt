package com.charaminstra.pleon

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


const val SPLASH_VIEW_TIME_DURATION = 1000L

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val TAG = javaClass.name
    private val _authSuccess = MutableLiveData<Boolean>()
//    private val _refreshSuccess = MutableLiveData<Boolean>()
    val authSuccess : LiveData<Boolean> = _authSuccess
//    val refreshSuccess : LiveData<Boolean> = _refreshSuccess

    private val _authName = MutableLiveData<String>()
    val authName : LiveData<String> = _authName

    fun getData() = authSuccess

    init {
        loadAuth()
    }

    fun loadAuth(){
        viewModelScope.launch {
            delay(SPLASH_VIEW_TIME_DURATION)
            val data = repository.getAuth()
            Log.i(TAG, "data -> $data")
            when (data.isSuccessful) {
                true -> {
                    _authSuccess.postValue(true)
                    _authName.postValue(data.body()?.data?.nickname!!)
                    Log.i(TAG,"SUCCESS -> "+ data.toString())
                }
                else -> {
                    _authSuccess.postValue(false)
                    Log.i(TAG,"FAIL -> "+ data.toString())
                }
            }
        }
    }
}