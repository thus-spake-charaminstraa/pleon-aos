package com.charaminstra.pleon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.charaminstra.pleon.foundation.FeedRepository
import com.charaminstra.pleon.foundation.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val repository: ScheduleRepository
): ViewModel() {

    private val _data = MutableLiveData<Boolean>()
    val data : LiveData<Boolean> = _data

}