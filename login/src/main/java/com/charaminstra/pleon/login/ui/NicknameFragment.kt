package com.charaminstra.pleon.login.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.R
import com.charaminstra.pleon.login.SplashActivity.Companion.prefs
import com.charaminstra.pleon.login.UserCreateViewModel
import com.charaminstra.pleon.login.databinding.FragmentNicknameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NicknameFragment : Fragment() {
    private val TAG = javaClass.simpleName
    private lateinit var binding : FragmentNicknameBinding
    private val viewModel: UserCreateViewModel by viewModels()
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNicknameBinding.inflate(layoutInflater)
        initListeners()
        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
        return binding.root
    }

    private fun initObservers(){
        viewModel.userCreateResponse.observe(this, Observer {
            it.let{
                Log.i(TAG,"userCreateDataObject"+it.toString())
                prefs.setAccessToken(it.token.access_token)
                prefs.setRefreshToken(it.token.refresh_token)
                Log.d(TAG, "verify token"+prefs.getVerifyToken())
                Log.d(TAG, "access token"+prefs.getAccessToken())
                Log.d(TAG, "refresh token"+prefs.getRefreshToken())
                navController.navigate(R.id.nickname_fragment_to_plant_register_fragment)
            }
        })
    }

    private fun initListeners(){
        binding.nextBtn.setOnClickListener {
            viewModel.userCreate(binding.nicknameEt.text.toString())

        }
    }
}
//eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIrODIxMDY2MzI5ODcyIiwiaWF0IjoxNjU3NzAyOTc5LCJleHAiOjE2NTc3MDQ3Nzl9.kk6gOQMltdu7Ll6VoVxXe_s8BPDLF2KFfQiqS-iUZig