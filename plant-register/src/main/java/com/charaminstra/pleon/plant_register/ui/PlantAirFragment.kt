package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.plant_register.AirType
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantAirBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantAirFragment : Fragment() {
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    private lateinit var binding: FragmentPlantAirBinding
    private var isChecking: Boolean = true
    private var mCheckedId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentPlantAirBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }

        /* bright, half_bright, lamp, dark */
        binding.airOne.text = resources.getString(AirType.YES.descId)
        binding.airTwo.text = resources.getString(AirType.WINDOW.descId)
        binding.airThree.text = resources.getString(AirType.NO.descId)

        radioGroupSet()
        binding.completeBtn.setOnClickListener {
            if(setAirType()){
                viewModel.postPlant()
                activity?.finish()
            }
        }
        return binding.root
    }

    fun radioGroupSet(){
        binding.airRg1.setOnCheckedChangeListener { radioGroup, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.airRg2.clearCheck()
                mCheckedId = checkedId
            }
            isChecking = true
        }
        binding.airRg2.setOnCheckedChangeListener { radioGroup, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.airRg1.clearCheck()
                mCheckedId = checkedId
            }
            isChecking = true
        }
    }
    fun setAirType() : Boolean {
        if (mCheckedId == binding.airOne.id) {
            viewModel.setAir(AirType.YES.apiString)
            return true
        } else if (mCheckedId == binding.airTwo.id) {
            viewModel.setAir(AirType.WINDOW.apiString)
            return true
        } else if (mCheckedId == binding.airThree.id) {
            viewModel.setAir(AirType.NO.apiString)
            return true
        }else{
            Toast.makeText(activity, R.string.choose_msg, Toast.LENGTH_LONG).show()
            return false
        }
    }
}


