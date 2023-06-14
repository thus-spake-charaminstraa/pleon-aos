package com.charaminstra.pleon.doctor.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.doctor.DoctorPlantAdapter
import com.charaminstra.pleon.doctor.DoctorViewModel
import com.charaminstra.pleon.doctor.R
import com.charaminstra.pleon.doctor.databinding.FragmentQPlantChooseBinding
import com.charaminstra.pleon.feed_common.PlantsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QPlantChooseFragment : Fragment() {
    private lateinit var binding : FragmentQPlantChooseBinding
    private lateinit var doctorPlantAdapter : DoctorPlantAdapter
    private val viewModel: DoctorViewModel by activityViewModels()
    private val plantViewModel: PlantsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQPlantChooseBinding.inflate(inflater, container, false)
        val navController = this.findNavController()
        binding.qPlantChooseBackBtn.setOnClickListener{
            navController.popBackStack()
        }
        binding.qPlantChooseNextBtn.setOnClickListener {
            navController.navigate(R.id.q_plant_choose_fragment_to_camera_fragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initObservers()
        plantViewModel.loadData()
        binding.doctorPlantRecyclerview.adapter = doctorPlantAdapter

    }
    private fun initList(){
        doctorPlantAdapter = DoctorPlantAdapter()
        doctorPlantAdapter.onItemClicked = {
            viewModel.plantId = it
        }
    }
    private fun initObservers(){
        plantViewModel.plantsList.observe(viewLifecycleOwner, Observer {
            doctorPlantAdapter.refreshItems(it)
        })
    }
}