package com.charaminstra.pleon

import android.util.Log
import com.charaminstra.pleon.common.api.PleonPreference
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class PleonFirebaseMessageService : FirebaseMessagingService(){
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val prefs = PleonPreference(applicationContext)
        prefs.setDeviceToken(token)
        Log.d("PleonFirebaseMessageService", "onNewToken: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("PleonFirebaseMessageService", "onMessageReceived: $message")
    }
}