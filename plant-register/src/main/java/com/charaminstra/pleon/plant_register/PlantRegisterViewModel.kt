package com.charaminstra.pleon.plant_register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.ImageRepository
import com.charaminstra.pleon.foundation.PlantIdRepository
import com.charaminstra.pleon.foundation.model.PlantDataObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.InputStream
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PlantRegisterViewModel @Inject constructor(private val repository: PlantIdRepository,
                                                 private val imageRepository: ImageRepository) : ViewModel() {

    private val TAG = javaClass.name
    private val _plantRegisterSuccess = MutableLiveData<Boolean>()
    val plantRegisterSuccess : LiveData<Boolean> = _plantRegisterSuccess

    private val _data = MutableLiveData<PlantDataObject>()
    val data: LiveData<PlantDataObject> = _data

    private val _patchSuccess = MutableLiveData<Boolean>()
    val patchSuccess: LiveData<Boolean> = _patchSuccess

    private val _deleteSuccess = MutableLiveData<Boolean>()
    val deleteSuccess: LiveData<Boolean> = _deleteSuccess

    val name = MutableLiveData<String>()
    val species = MutableLiveData<String>()
    val water_date = MutableLiveData<String>()
    val adopt_date = MutableLiveData<String>()
    val light = MutableLiveData<String>()
    val air = MutableLiveData<String>()
    //val thumbnail = MutableLiveData<String>()
    private val _urlResponse = MutableLiveData<String?>()
    val urlResponse : LiveData<String?> = _urlResponse

    fun getName(): LiveData<String> {
        return name
    }
    fun setName(value: String) {
        name.value = value
    }
    fun getSpecies(): LiveData<String> {
        return species
    }
    fun setSpecies(value: String) {
        species.value = value
    }
    fun getWater_date(): LiveData<String> {
        return water_date
    }
    fun setWater_date(value: String) {
        water_date.value = value
    }
    fun getAdopt_date(): LiveData<String> {
        return adopt_date
    }
    fun setAdopt_date(value: String) {
        adopt_date.value = value
    }
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
    fun setUrl(url: String){
        _urlResponse.value = url
    }

    fun postPlant(){
        viewModelScope.launch {
            val data = repository.postPlant(
                getName().value.toString(),
                getSpecies().value.toString(),
                getWater_date().value.toString(),
                getAdopt_date().value.toString(),
                urlResponse.value.toString(),
                getLight().value.toString(),
                getAir().value.toString())
            Log.i("plant post","\n light:  ${getLight().value.toString()}")
            _plantRegisterSuccess.postValue(data.body()?.success)
            Log.i(TAG,"DATA -> $data"+"\n"+data)
            Log.i(TAG,"DATA.body -> $data"+"\n"+data.body())
        }
    }

    fun loadData(id: String){
        viewModelScope.launch {
            val data = repository.getPlantId(id)
            when (data.isSuccessful) {
                true -> {
                    _data.postValue(data.body()?.data!!)
                    setUrl(data.body()?.data?.thumbnail!!)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
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

    fun setNoImg(){
        _urlResponse.postValue(null)
    }
}