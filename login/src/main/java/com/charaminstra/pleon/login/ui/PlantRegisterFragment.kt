package com.charaminstra.pleon.login.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.R
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
            startHomeActivity()
        }
        binding.nextBtn.setOnClickListener {
            navController.navigate(R.id.plant_register_fragment_to_plant_place_fragment)
        }
        binding.cameraBtn.setOnClickListener {
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