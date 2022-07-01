package com.charaminstra.pleon.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        //val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_login)

        val navController = this.findNavController()
        binding.loginBtn.setOnClickListener {
            navController.navigate(R.id.login_fragment_to_phone_fragment)
            //findNavController().navigate(R.id.login_fragment_to_phone_fragment)
            //LoginActivity().navigate(R.id.login_fragment_to_phone_fragment)
        }
        return binding.root
    }
}