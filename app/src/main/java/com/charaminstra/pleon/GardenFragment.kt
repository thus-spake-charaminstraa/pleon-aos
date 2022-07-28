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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.adapter.CommonAdapter
import com.charaminstra.pleon.databinding.FragmentGardenBinding
import com.charaminstra.pleon.login.AuthViewModel
import com.charaminstra.pleon.plant_register.ui.PlantRegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenFragment : Fragment() {
    private val viewModel: PlantsViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var binding : FragmentGardenBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentGardenBinding.inflate(layoutInflater)
        navController = this.findNavController()
        viewModel.getData().observe(this, Observer {
            val adapter = CommonAdapter(it,"GARDEN_PLANT",onItemClicked = { plantId->
                val bundle = Bundle()
                bundle.putString("id",plantId)
                navController.navigate(R.id.view_pager_fragment_to_plant_detail_fragment,bundle)})
            binding.recyclerview.adapter = adapter
        })

        authViewModel.getName().observe(this, Observer {
            binding.gardenFragmentTitle.text = it + "님의 Garden"
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