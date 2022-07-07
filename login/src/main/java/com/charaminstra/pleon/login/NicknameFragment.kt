package com.charaminstra.pleon.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.databinding.FragmentNicknameBinding

class NicknameFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNicknameBinding.inflate(inflater, container, false)
        val navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
        binding.nextBtn.setOnClickListener {
            navController.navigate(R.id.nickname_fragment_to_plant_register_fragment)
        }
        return binding.root
    }
}