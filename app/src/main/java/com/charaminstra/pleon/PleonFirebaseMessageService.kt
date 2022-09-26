package com.charaminstra.pleon

import android.util.Log
import androidx.fragment.app.viewModels
import com.charaminstra.pleon.foundation.UserRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PleonFirebaseMessageService : FirebaseMessagingService(){
    @Inject
    lateinit var repository : UserRepository
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("PleonFirebaseMessageService", "onNewToken: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("PleonFirebaseMessageService", "onMessageReceived: $message")
    }
}