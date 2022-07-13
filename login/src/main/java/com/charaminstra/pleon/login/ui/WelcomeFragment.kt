package com.charaminstra.pleon.login.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.R

class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = com.charaminstra.pleon.login.databinding.FragmentWelcomeBinding.inflate(inflater, container, false)
        binding.helloText.text = "마틸다 님,\n"+getString(R.string.welcome_fragment_title)
        val navController = this.findNavController()
        binding.startBtn.setOnClickListener {
            startHomeActivity()
        }
        return binding.root
    }

    private fun startHomeActivity() {
        val intent = Intent(
            requireContext(),
            Class.forName("com.charaminstra.pleon.HomeActivity")
        )
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}