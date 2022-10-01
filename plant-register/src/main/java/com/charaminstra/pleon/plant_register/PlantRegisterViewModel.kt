package com.charaminstra.pleon.plant_register

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.ImageRepository
import com.charaminstra.pleon.foundation.InferenceRepository
import com.charaminstra.pleon.foundation.PlantIdRepository
import com.charaminstra.pleon.foundation.model.PlantDataObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class PlantRegisterViewModel @Inject constructor(private val repository: PlantIdRepository,
                                                 private val imageRepository: ImageRepository,
                                                 private val inferenceRepository: InferenceRepository) : ViewModel() {

    private val TAG = javaClass.name

    private val _plantRegisterSuccess = MutableLiveData<Boolean>()
    val plantRegisterSuccess : LiveData<Boolean> = _plantRegisterSuccess

    private val _plantDetectionSuccess = MutableLiveData<Boolean?>()
    val plantDetectionSuccess : LiveData<Boolean?> = _plantDetectionSuccess

    private val _plantDetectionResultLabel = MutableLiveData<String>()
    val plantDetectionResultLabel : LiveData<String> = _plantDetectionResultLabel

    private val _plantDetectionResultPercent= MutableLiveData<Float>()
    val plantDetectionResultPercent : LiveData<Float> = _plantDetectionResultPercent

    private val _data = MutableLiveData<PlantDataObject>()
    val data: LiveData<PlantDataObject> = _data

    val name = MutableLiveData<String>()
    val species = MutableLiveData<String>()
    val water_date = MutableLiveData<String>()
    val adopt_date = MutableLiveData<String>()
    val light = MutableLiveData<String>()
    val air = MutableLiveData<String>()

    private val _thumbnailUrlResponse = MutableLiveData<String?>()
    val thumbnailUrlResponse : LiveData<String?> = _thumbnailUrlResponse

    private val _plantDetectionUrlResponse = MutableLiveData<String?>()
    val plantDetectionUrlResponse : LiveData<String?> = _plantDetectionUrlResponse

    var imgType : String? = null

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
    fun warmingPlantDetectionModel(){
        viewModelScope.launch {
            inferenceRepository.warmingPlantDetection()
        }
    }

    fun clearPlantDetectionUrl(){
        _plantDetectionUrlResponse.value = null
    }

    fun clearPlantDetectionSuccess(){
        _plantDetectionSuccess.value = null
    }

    fun postPlantDetectionModel(){
        viewModelScope.launch {
            val data = inferenceRepository.postPlantDetection(plantDetectionUrlResponse.value.toString())
            Log.i(TAG,"plant detection data -> $data")
            when(data.isSuccessful){
                true -> {
                    Log.i(TAG,"plant detection data ->"+data.body().toString())
                    if(data.body()?.success == false){
                        _plantDetectionSuccess.postValue(false)
                    }else{
                        _plantDetectionSuccess.postValue(true)
                        _plantDetectionResultLabel.postValue(data.body()?.species?.name)
                        _plantDetectionResultPercent.postValue(data.body()?.score!!)
                    }
                }else -> {
                    Log.i(TAG,"plant detection error")
                }
            }
        }
    }

    fun thumbnailCameraToUrl(inputStream: InputStream){
        viewModelScope.launch {
            val data =imageRepository.postImage(inputStream)
            Log.i(TAG,"data -> $data")
            when (data.isSuccessful) {
                true -> {
                    Log.i(TAG,"data.body -> "+data.body())
                    _thumbnailUrlResponse.value = data.body()?.data?.url
                    _plantDetectionUrlResponse.value = data.body()?.data?.url
                }
                else -> {
                    Log.i(TAG,"FAIL-> ")
                }
            }
        }
    }
    fun thumbnailGalleryToUrl(bitmap: Bitmap){
        viewModelScope.launch {
            ByteArrayOutputStream().use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val inputStream = ByteArrayInputStream(stream.toByteArray())
                val data = imageRepository.postImage(inputStream)
                Log.i(TAG,"data -> $data")
                when (data.isSuccessful) {
                    true -> {
                        Log.i(TAG,"data.body -> "+data.body())
                        _thumbnailUrlResponse.value = data.body()?.data?.url
                        _plantDetectionUrlResponse.value = data.body()?.data?.url
                    }
                    else -> {
                        Log.i(TAG,"FAIL-> ")
                    }
                }
            }
        }
    }

    fun speciesCameraToUrl(inputStream: InputStream){
        viewModelScope.launch {
            val data =imageRepository.postImage(inputStream)
            Log.i(TAG,"data -> $data")
            when (data.isSuccessful) {
                true -> {
                    Log.i(TAG,"data.body -> "+data.body())
                    _plantDetectionUrlResponse.value = data.body()?.data?.url
                }
                else -> {
                    Log.i(TAG,"FAIL-> ")
                }
            }
        }
    }
    fun speciesGalleryToUrl(bitmap: Bitmap){
        viewModelScope.launch {
            ByteArrayOutputStream().use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val inputStream = ByteArrayInputStream(stream.toByteArray())
                val data = imageRepository.postImage(inputStream)
                Log.i(TAG,"data -> $data")
                when (data.isSuccessful) {
                    true -> {
                        Log.i(TAG,"data.body -> "+data.body())
                        _plantDetectionUrlResponse.value = data.body()?.data?.url
                    }
                    else -> {
                        Log.i(TAG,"FAIL-> ")
                    }
                }
            }
        }
    }

    fun setUrl(url: String){
        _thumbnailUrlResponse.value = url
    }

    fun postPlant(){
        viewModelScope.launch {
            val data = repository.postPlant(
                getName().value.toString(),
                getSpecies().value.toString(),
                getWater_date().value.toString(),
                getAdopt_date().value.toString(),
                thumbnailUrlResponse.value.toString(),
                getLight().value.toString(),
                getAir().value.toString())
            Log.i("plant post:: ","\n"+getName().value.toString()+"\n"+getSpecies().value.toString()+"\n"+ getWater_date().value.toString()+"\n"+getAdopt_date().value.toString()+"\n"+
                    thumbnailUrlResponse.value.toString()+"\n"+getLight().value.toString()+"\n"+getAir().value.toString())
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
}