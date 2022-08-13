package com.charaminstra.pleon

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.UserRepository
import com.charaminstra.pleon.login.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val TAG = javaClass.name
    private val _userName =  MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    private val _userImgUrl =  MutableLiveData<String>()
    val userImgUrl : LiveData<String> = _userImgUrl

    private val _updateUserDataSuccess =  MutableLiveData<Boolean>()
    val updateUserDataSuccess : LiveData<Boolean> = _updateUserDataSuccess

    fun getUserData(){
        viewModelScope.launch {
            val data = authRepository.getAuth()
            when (data.isSuccessful) {
                true -> {
                    _userName.postValue(data.body()?.data?.nickname!!)
                    _userImgUrl.postValue(data.body()?.data?.thumbnail!!)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    fun updateUserData(name: String, thumbnail: String){
        viewModelScope.launch {
            val data = userRepository.patchUserData(name,thumbnail)
            Log.i(TAG, "patch DATA"+data.body())
            when (data.isSuccessful) {
                true -> {
                    _updateUserDataSuccess.postValue(data.body()?.success!!)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    _updateUserDataSuccess.postValue(data.body()?.success!!)
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }
}