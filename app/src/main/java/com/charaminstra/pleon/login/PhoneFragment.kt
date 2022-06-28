package com.charaminstra.pleon.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.charaminstra.pleon.R
import com.charaminstra.pleon.databinding.FragmentPhoneBinding

class PhoneFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPhoneBinding.inflate(inflater, container, false)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
        binding.checkBtn.setOnClickListener {
            navController.navigate(R.id.phone_fragment_to_nickname_fragment)
        }
        return binding.root
    }
}