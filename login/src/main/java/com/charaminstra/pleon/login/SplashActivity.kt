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
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        prefs= PreferenceUtil(this)
        Log.d("SplashActivity", "verify token"+prefs.getVerifyToken())
        Log.d("SplashActivity", "access token"+prefs.getAccessToken())
        Log.d("SplashActivity", "refresh token"+prefs.getRefreshToken())

        viewModel.getData().observe(this, Observer {
            Log.d("SplashActivity", "t.isSuccessful : "+it.isSuccessful)
            if(it.isSuccessful){
                Log.d("SplashActivity", "t.isSuccessful : "+it.isSuccessful)
                startHomeActivity(this)
            }else{
                Log.d("SplashActivity", "fail : go login")
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        })

//        val TEST = false
//
//        val handler = Handler()
//        handler.postDelayed(Runnable {
//            if(TEST){
//                startHomeActivity(this)
//            }else{
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }, 1000) //딜레이 타임 조절

    }
}