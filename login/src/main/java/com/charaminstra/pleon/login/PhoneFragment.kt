package com.charaminstra.pleon.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.foundation.ApiWrapper
import com.charaminstra.pleon.login.databinding.FragmentPhoneBinding

class PhoneFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPhoneBinding.inflate(inflater, container, false)
        val navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
        binding.checkBtn.setOnClickListener {
            val phone = "01011112222"
            val code = "777777"
            ApiWrapper.postTest(phone,code) {
                if (it.success) {
                    Log.i("PhoneFragment", "success")
                    navController.navigate(R.id.phone_fragment_to_nickname_fragment)
                }
            }

        }
        return binding.root
    }
}