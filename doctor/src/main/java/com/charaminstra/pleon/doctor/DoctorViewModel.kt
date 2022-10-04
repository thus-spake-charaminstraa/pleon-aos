package com.charaminstra.pleon.doctor

import com.charaminstra.pleon.foundation.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(private val imageRepository: ImageRepository) {
}