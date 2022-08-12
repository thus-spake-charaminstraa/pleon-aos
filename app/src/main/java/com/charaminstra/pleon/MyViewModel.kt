package com.charaminstra.pleon

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.api.PleonPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val prefs: PleonPreference): ViewModel() {
    private val TAG = javaClass.name
    private val _userName =  MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    private val _userImgUrl =  MutableLiveData<String?>()
    val userImgUrl : LiveData<String?> = _userImgUrl

    fun getUserName(){
        viewModelScope.launch {
            Log.i(TAG,"get User Name DATA -> "+prefs.getName())
            _userName.postValue(prefs.getName())
        }
    }

    fun getUserImgUrl(){
        viewModelScope.launch {
            Log.i(TAG,"get User Img Url DATA -> "+prefs.getImage())
            _userImgUrl.postValue(prefs.getImage())
        }
    }

}