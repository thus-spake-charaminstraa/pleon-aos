package com.charaminstra.pleon.garden

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.common.data.PlantDataObject
import com.charaminstra.pleon.common.repository.ImageRepository
import com.charaminstra.pleon.common.repository.PlantIdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class PlantEditViewModel @Inject constructor(
    private val repository: PlantIdRepository,
    private val imageRepository: ImageRepository
): ViewModel() {
    private val TAG = javaClass.name

    private val _plantData = MutableLiveData<PlantDataObject>()
    val plantData: LiveData<PlantDataObject> = _plantData

    private val _urlResponse = MutableLiveData<String?>()
    val urlResponse : LiveData<String?> = _urlResponse

    private val _patchSuccess = MutableLiveData<Boolean>()
    val patchSuccess: LiveData<Boolean> = _patchSuccess

    private val _deleteSuccess = MutableLiveData<Boolean>()
    val deleteSuccess: LiveData<Boolean> = _deleteSuccess

    val name = MutableLiveData<String>()
    val light = MutableLiveData<String>()
    val air = MutableLiveData<String>()

    fun getLight(): LiveData<String> {
        return light
    }
    fun setLight(value: String) {
        light.value = value
    }
    fun getAir(): LiveData<String> {
        return air
    }
    fun setAir(value: String) {
        air.value = value
    }

    fun getPlantData(id: String) {
        viewModelScope.launch {
            val data = repository.getPlantId(id)
            when (data.isSuccessful) {
                true -> {
                    _plantData.postValue(data.body()?.data!!)
                    _urlResponse.postValue(data.body()?.data?.thumbnail)
                    Log.i(TAG, "SUCCESS -> " + data.body().toString())
                }
                else -> {
                    Log.i(TAG, "FAIL -> " + data.body().toString())
                }
            }
        }
    }

    private val _plantImgBitmap = MutableLiveData<Bitmap?>()
    var plantImgBitmap : LiveData<Bitmap?> = _plantImgBitmap

    fun setBitmap(bitmap: Bitmap){
        viewModelScope.launch {
            _plantImgBitmap.value = bitmap
        }
    }

    var plantImgEdit = false

    fun plantImgBitmapToUrl(){
        viewModelScope.launch {
            ByteArrayOutputStream().use { stream ->
                plantImgBitmap.value?.compress(Bitmap.CompressFormat.JPEG, 70, stream)
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

    fun patchData(id: String,
                  name: String,
                  adopt_date: String){
        viewModelScope.launch {
            val data = repository.patchPlantId(id,name,adopt_date,
                urlResponse.value.toString(),
                getLight().value.toString(),
                getAir().value.toString())
            Log.i(TAG, "patch DATA"+data.body())
            when (data.isSuccessful) {
                true -> {
                    _patchSuccess.postValue(data.body()?.success)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }
    fun deleteData(id: String){
        viewModelScope.launch {
            val data = repository.deletePlantId(id)
            Log.i(TAG, "delete DATA"+data.body())
            when (data.isSuccessful) {
                true -> {
                    _deleteSuccess.postValue(data.body()?.success)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

}