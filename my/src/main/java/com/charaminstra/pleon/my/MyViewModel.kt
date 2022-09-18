package com.charaminstra.pleon.my

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.AuthRepository
import com.charaminstra.pleon.foundation.ImageRepository
import com.charaminstra.pleon.foundation.UserRepository
import com.charaminstra.pleon.foundation.api.PleonPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val imageRepository: ImageRepository,
    private val prefs : PleonPreference
): ViewModel() {
    private val TAG = javaClass.name
    private val _userName =  MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    private val _updateUserDataSuccess =  MutableLiveData<Boolean>()
    val updateUserDataSuccess : LiveData<Boolean> = _updateUserDataSuccess

    private val _urlResponse = MutableLiveData<String?>()
    val urlResponse : LiveData<String?> = _urlResponse

    fun getUserData(){
        viewModelScope.launch {
            val data = authRepository.getAuth()
            when (data.isSuccessful) {
                true -> {
                    _userName.postValue(data.body()?.data?.nickname!!)
                    _urlResponse.value=data.body()?.data?.thumbnail!!
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    fun updateUserData(name: String){
        viewModelScope.launch {
            val data = userRepository.patchUserData(name,urlResponse.value.toString())
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
    fun setImgToUrl(image_stream: InputStream){
        viewModelScope.launch {
            val data =imageRepository.postImage(image_stream)
            Log.i(TAG,"data -> $data")
            when (data.isSuccessful) {
                true -> {
                    Log.i(TAG,"data.body -> "+data.body())
                    _urlResponse.postValue(data.body()?.data?.url)
                }
                else -> {
                    Log.i(TAG,"FAIL-> ")
                }
            }
        }
    }
    fun setNoImg(){
        _urlResponse.postValue("")
    }

    fun deletePreference(){
        prefs.delete()
    }
}