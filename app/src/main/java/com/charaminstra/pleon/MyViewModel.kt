package com.charaminstra.pleon

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.api.PleonPreference
import com.charaminstra.pleon.foundation.model.AuthResponse
import com.charaminstra.pleon.login.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {
    private val TAG = javaClass.name
    private val _userName =  MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    private val _userImgUrl =  MutableLiveData<String>()
    val userImgUrl : LiveData<String> = _userImgUrl

    fun getUserData(){
        viewModelScope.launch {
            val data = repository.getAuth()
            when (data.isSuccessful) {
                true -> {
                    _userName.value = data.body()?.data?.nickname!!
                    _userImgUrl.value = data.body()?.data?.thumbnail!!
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }
}