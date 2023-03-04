package com.charaminstra.pleon

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.charaminstra.pleon.common.api.PleonPreference
import com.charaminstra.pleon.login.startHomeActivity
import com.charaminstra.pleon.login.ui.LoginActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val TAG = javaClass.name
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val prefs= PleonPreference(applicationContext)
        getFirebaseToken()
        Log.d(TAG, "verify token"+prefs.getVerifyToken())
        Log.d(TAG, "access token"+prefs.getAccessToken())
        Log.d(TAG, "refresh token"+prefs.getRefreshToken())

        viewModel.getData().observe(this, Observer {
            Log.d(TAG, "from auth view model get data: $it")
            if(it){
                startHomeActivity(this)
                finish()
            }else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        viewModel.loadAuth()
    }

    private fun getFirebaseToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(TAG,"Fetching FCM registration token failed ${task.exception}")
                return@OnCompleteListener
            }

            val token = task.result
            Log.d(TAG,"token=${token}")
        })
    }
}