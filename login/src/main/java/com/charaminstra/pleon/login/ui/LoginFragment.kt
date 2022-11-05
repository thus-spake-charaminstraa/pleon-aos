package com.charaminstra.pleon.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.R
import com.charaminstra.pleon.login.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        val navController = this.findNavController()
        binding.phoneLoginBtn.setOnClickListener {
            navController.navigate(R.id.login_fragment_to_phone_fragment)
        }
        return binding.root
    }
}