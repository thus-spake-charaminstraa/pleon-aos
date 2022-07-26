package com.charaminstra.pleon

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charaminstra.pleon.databinding.FragmentGardenBinding
import com.charaminstra.pleon.plant_register.ui.PlantRegisterActivity


class GardenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGardenBinding.inflate(inflater, container, false)

        binding.circleBtn.setOnClickListener {
            val intent = Intent(context, PlantRegisterActivity::class.java)
            intent.putExtra("from","main")
            startActivity(intent)
        }

        return binding.root


    }

}