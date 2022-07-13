package com.charaminstra.pleon.login.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.R
import com.charaminstra.pleon.login.UserCreateViewModel
import com.charaminstra.pleon.login.databinding.FragmentNicknameBinding
import com.charaminstra.pleon.login.ui.LoginActivity.Companion.prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NicknameFragment : Fragment() {
    private lateinit var binding : FragmentNicknameBinding
    private val viewModel: UserCreateViewModel by viewModels()
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
        val navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
        binding.nextBtn.setOnClickListener {
            navController.navigate(R.id.nickname_fragment_to_plant_register_fragment)
        }
        return binding.root
    }

    private fun initObservers(){
        viewModel.userCreateResponse.observe(this, Observer {
            it.let{
                Log.d("user create Response Body: ", it.toString())
                prefs.setAccessToken(it.token.access_token)
                prefs.setRefreshToken(it.token.refresh_token)
            }
        })
    }

    private fun initListeners(){
        binding.nextBtn.setOnClickListener {
            viewModel.userCreate(binding.nicknameEt.text.toString())
        }
    }
}