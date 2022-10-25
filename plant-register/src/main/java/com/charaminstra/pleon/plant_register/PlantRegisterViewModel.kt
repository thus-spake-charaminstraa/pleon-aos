package com.charaminstra.pleon.plant_register

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
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
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class PlantRegisterViewModel @Inject constructor(private val repository: PlantIdRepository,
                                                 private val imageRepository: ImageRepository,
                                                 private val inferenceRepository: InferenceRepository) : ViewModel() {

    private val TAG = javaClass.name

    var thumbnailNextStep = false
    var speciesNextStep = false

    private val _plantRegisterSuccess = MutableLiveData<Boolean>()
    val plantRegisterSuccess : LiveData<Boolean> = _plantRegisterSuccess

    private val _plantDetectionSuccess = MutableLiveData<Boolean?>()
    val plantDetectionSuccess : LiveData<Boolean?> = _plantDetectionSuccess

    private val _plantDetectionResultLabel = MutableLiveData<String>()
    val plantDetectionResultLabel : LiveData<String> = _plantDetectionResultLabel

    private val _plantDetectionResultPercent= MutableLiveData<Float>()
    val plantDetectionResultPercent : LiveData<Float> = _plantDetectionResultPercent

    private val _plantDetectionResultDifficulty = MutableLiveData<String>()
    val plantDetectionResultDifficulty : LiveData<String> = _plantDetectionResultDifficulty

    private val _plantDetectionResultBenefit = MutableLiveData<String>()
    val plantDetectionResultBenefit : LiveData<String> = _plantDetectionResultBenefit

    private val _plantDetectionResultTip = MutableLiveData<String>()
    val plantDetectionResultTip : LiveData<String> = _plantDetectionResultTip

    val name = MutableLiveData<String>()
    private val species = MutableLiveData<String>()
    private val water_date = MutableLiveData<String>()
    private val adopt_date = MutableLiveData<String>()
    private val light = MutableLiveData<String>()
    private val air = MutableLiveData<String>()

    private val _thumbnailBitmap = MutableLiveData<Bitmap?>()
    var thumbnailBitmap : LiveData<Bitmap?> = _thumbnailBitmap

    private val _thumbnailUrlResponse = MutableLiveData<String?>()
    val thumbnailUrlResponse : LiveData<String?> = _thumbnailUrlResponse

    private val _plantDetectionUrlResponse = MutableLiveData<String?>()
    val plantDetectionUrlResponse : LiveData<String?> = _plantDetectionUrlResponse

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
                        _plantDetectionResultDifficulty.postValue(data.body()?.species?.managing_difficulty)
                        _plantDetectionResultBenefit.postValue(data.body()?.species?.benefit)
                        _plantDetectionResultTip.postValue(data.body()?.species?.tip)
                    }
                }else -> {
                    Log.i(TAG,"plant detection error")
                }
            }
        }
    }

    fun setBitmap(bitmap: Bitmap){
        viewModelScope.launch {
            _thumbnailBitmap.value = bitmap
        }
    }

    fun thumbnailBitmapToUrl(){
        viewModelScope.launch {
            ByteArrayOutputStream().use { stream ->
                thumbnailBitmap.value?.compress(Bitmap.CompressFormat.JPEG, 70, stream)
                val inputStream = ByteArrayInputStream(stream.toByteArray())
                val data = imageRepository.postImage(inputStream)
                Log.i(TAG,"data -> $data")
                when (data.isSuccessful) {
                    true -> {
                        Log.i(TAG,"data.body -> "+data.body())
                        _thumbnailUrlResponse.postValue(data.body()?.data?.url)
                    }
                    else -> {
                        Log.i(TAG,"FAIL-> ")
                    }
                }
            }
        }
    }

    fun thumbnailToSpecies(){
        _plantDetectionUrlResponse.value = thumbnailUrlResponse.value
    }

    fun speciesBitmapToUrl(bitmap: Bitmap){
        viewModelScope.launch {
            ByteArrayOutputStream().use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
                val inputStream = ByteArrayInputStream(stream.toByteArray())
                val data = imageRepository.postImage(inputStream)
                Log.i(TAG,"data -> $data")
                when (data.isSuccessful) {
                    true -> {
                        Log.i(TAG,"data.body -> "+data.body())
                        _plantDetectionUrlResponse.postValue(data.body()?.data?.url)
                    }
                    else -> {
                        Log.i(TAG,"FAIL-> ")
                    }
                }
            }
        }
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
}