package com.charaminstra.pleon.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charaminstra.pleon.login.R
import com.charaminstra.pleon.login.startHomeActivity

class WelcomeFragment : Fragment() {
    private val TAG = javaClass.simpleName
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = com.charaminstra.pleon.login.databinding.FragmentWelcomeBinding.inflate(inflater, container, false)
        binding.helloText.text = "안녕하세요."
        binding.startBtn.setOnClickListener {
            startHomeActivity(requireContext())
        }
        return binding.root
    }
}