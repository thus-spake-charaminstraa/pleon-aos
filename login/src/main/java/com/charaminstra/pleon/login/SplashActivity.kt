package com.charaminstra.pleon.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.charaminstra.pleon.common.PreferenceUtil
import com.charaminstra.pleon.login.ui.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    private val TAG = javaClass.name
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        prefs= PreferenceUtil(this)
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
    }
}