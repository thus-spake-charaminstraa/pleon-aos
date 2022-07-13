package com.charaminstra.pleon

import android.app.Application
import com.charaminstra.pleon.common.PreferenceUtil
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class PleonApplication: Application() {
    override fun onCreate() {
        super.onCreate()

    }
}