package com.charaminstra.pleon

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.charaminstra.pleon.databinding.FragmentGardenBinding
import com.charaminstra.pleon.login.UserCreateViewModel
import com.charaminstra.pleon.plant_register.ui.PlantRegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenFragment : Fragment() {
    private val viewModel: PlantsViewModel by viewModels()
    private lateinit var binding : FragmentGardenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentGardenBinding.inflate(layoutInflater)
        viewModel.getData().observe(this, Observer {
            Log.i("list",it.toString())
            val adapter = PlantAdapter(it,"GARDEN")
            binding.recyclerview.adapter = adapter
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.circleBtn.setOnClickListener {
            val intent = Intent(context, PlantRegisterActivity::class.java)
            intent.putExtra("from","main")
            startActivity(intent)
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //viewmodel update
        viewModel.loadData()
    }
}