package com.charaminstra.pleon.my

import android.graphics.Bitmap
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
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
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
                    _urlResponse.postValue(data.body()?.data?.thumbnail!!)
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
            Log.i("haha;;",name+"\n"+urlResponse.value.toString())
            val data = userRepository.patchUserData(name,urlResponse.value.toString())
            Log.i(TAG, "patch DATA"+data.body())
            when (data.isSuccessful) {
                true -> {
                    _updateUserDataSuccess.postValue(true)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    _updateUserDataSuccess.postValue(false)
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }
    fun cameraToUrl(inputStream: InputStream){
        viewModelScope.launch {
            val data =imageRepository.postImage(inputStream)
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
    fun galleryToUrl(bitmap: Bitmap){
        viewModelScope.launch {
            ByteArrayOutputStream().use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val inputStream = ByteArrayInputStream(stream.toByteArray())
                val data = imageRepository.postImage(inputStream)
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
    }

    fun deletePreference(){
        prefs.delete()
    }
}