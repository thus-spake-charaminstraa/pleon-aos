package com.charaminstra.pleon.login.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.charaminstra.pleon.foundation.KAKAO_NATIVE_APP_KEY
import com.charaminstra.pleon.login.R
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
    }
}