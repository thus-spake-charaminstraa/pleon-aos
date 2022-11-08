package com.charaminstra.pleon

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class PleonApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}