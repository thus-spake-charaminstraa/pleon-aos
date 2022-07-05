package com.charaminstra.pleon.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.databinding.FragmentPlantRegisterBinding

class PlantRegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlantRegisterBinding.inflate(inflater, container, false)
        val navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
        binding.skipBtn.setOnClickListener {
            //PreferenceUtil.setString("token", "01234")
            navController.navigate(R.id.plant_register_fragment_to_jump)
        }
        binding.cameraBtn.setOnClickListener {
        }
        return binding.root
    }
}