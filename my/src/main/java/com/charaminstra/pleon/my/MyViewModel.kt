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

    private val _commentPushSetting =  MutableLiveData<Boolean>()
    var commentPushSetting : LiveData<Boolean> = _commentPushSetting

    private val _guidePushSetting =  MutableLiveData<Boolean>()
    val guidePushSetting : LiveData<Boolean> = _guidePushSetting

    init {
        getUserData()
    }

    var imgEdit = false

    fun getUserData(){
        viewModelScope.launch {
            val data = authRepository.getAuth()
            when (data.isSuccessful) {
                true -> {
                    _userName.postValue(data.body()?.data?.nickname!!)
                    _urlResponse.postValue(data.body()?.data?.thumbnail!!)
                    _commentPushSetting.postValue(data.body()?.data?.comment_push_noti)
                    _guidePushSetting.postValue(data.body()?.data?.guide_push_noti)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    fun setCommentSetting(check: Boolean){
        viewModelScope.launch {
            _commentPushSetting.value = check
        }
    }

    fun setGuideSetting(check: Boolean){
        viewModelScope.launch {
            _guidePushSetting.value = check
        }
    }

    fun postPushSetting(){
        viewModelScope.launch {
            val data = userRepository.patchUserPushSetting(
                commentPushSetting.value!!, guidePushSetting.value!!
            )
            when(data.isSuccessful){
                true ->{
                    _commentPushSetting.postValue(data.body()?.data?.comment_push_noti)
                    _guidePushSetting.postValue(data.body()?.data?.guide_push_noti)
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

    private val _imgBitmap = MutableLiveData<Bitmap?>()
    var imgBitmap : LiveData<Bitmap?> = _imgBitmap

    fun setBitmap(bitmap: Bitmap){
        viewModelScope.launch {
            _imgBitmap.value = bitmap
        }
    }

    fun myBitmapToUrl(){
        viewModelScope.launch {
            ByteArrayOutputStream().use { stream ->
                imgBitmap.value?.compress(Bitmap.CompressFormat.JPEG, 70, stream)
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

    fun deleteDeviceTokenServer(){
        viewModelScope.launch {
            val data = userRepository.deleteDeviceToken()
            Log.i(TAG, "delete token DATA"+data.body())
            when (data.isSuccessful) {
                true -> {
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }
}