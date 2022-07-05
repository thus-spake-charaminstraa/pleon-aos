package com.charaminstra.pleon.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        binding.helloText.text = "마틸다 님,\n"+getString(R.string.welcome_fragment_title)
        val navController = this.findNavController()
        binding.startBtn.setOnClickListener {
            navController.navigate(R.id.welcome_fragment_to_jump)
        }
        return binding.root
    }
}