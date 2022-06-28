package com.charaminstra.pleon.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.charaminstra.pleon.MainActivity.Companion.prefs
import com.charaminstra.pleon.R
import com.charaminstra.pleon.databinding.FragmentPlantRegisterBinding

class PlantRegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlantRegisterBinding.inflate(inflater, container, false)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
//        binding.checkBtn.setOnClickListener {
//            navController.navigate(R.id.nickname_fragment)
//        }
        binding.skipBtn.setOnClickListener {
            prefs.setString("token", "01234")
            navController.navigate(R.id.plant_register_fragment_to_jump)
        }
        return binding.root
    }
}