package com.charaminstra.pleon.feed_common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.common.repository.PlantsRepository
import com.charaminstra.pleon.domain.model.Plant
import com.charaminstra.pleon.domain.usecase.GetPlantListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantsViewModel @Inject constructor(
    private val usecase: GetPlantListUseCase
) : ViewModel() {
    private val TAG = javaClass.name

    private val _plantsList = MutableLiveData<List<Plant>>()
    val plantsList : LiveData<List<Plant>> = _plantsList

    private val _plantsCount = MutableLiveData<Int>()
    val plantsCount : LiveData<Int> = _plantsCount

    init {
        loadData()
    }

    fun loadData(){
        viewModelScope.launch {
            usecase().collectLatest {
                _plantsList.postValue(it)
                _plantsCount.postValue(it.size)
            }
        }
    }
}