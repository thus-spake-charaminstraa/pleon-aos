package com.charaminstra.pleon

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.charaminstra.pleon.login.ui.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val TEST = false

        val handler = Handler()
        handler.postDelayed(Runnable {
            if(TEST){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 1000) //딜레이 타임 조절

    }
}