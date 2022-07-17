package com.charaminstra.pleon

import android.app.Application
import com.charaminstra.pleon.foundation.PleonPreference
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class PleonApplication: Application() {
    override fun onCreate() {
        super.onCreate()

    }
}