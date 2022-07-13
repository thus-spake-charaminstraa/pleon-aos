package com.charaminstra.pleon.login.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.R
import com.charaminstra.pleon.login.SplashActivity
import com.charaminstra.pleon.login.startHomeActivity

class WelcomeFragment : Fragment() {
    private val TAG = javaClass.simpleName
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = com.charaminstra.pleon.login.databinding.FragmentWelcomeBinding.inflate(inflater, container, false)
        binding.helloText.text = "마틸다 님,\n"+getString(R.string.welcome_fragment_title)
        Log.d(TAG, "verify token"+ SplashActivity.prefs.getVerifyToken())
        Log.d(TAG, "access token"+ SplashActivity.prefs.getAccessToken())
        Log.d(TAG, "refresh token"+ SplashActivity.prefs.getRefreshToken())
        binding.startBtn.setOnClickListener {
            startHomeActivity(requireContext())
        }
        return binding.root
    }
}