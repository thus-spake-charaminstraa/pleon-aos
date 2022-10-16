package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.common.AirType
import com.charaminstra.pleon.common.CLASS_NAME
import com.charaminstra.pleon.common.PLANT_REGISTER_CLICK
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantAirBinding
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantAirFragment : Fragment() {
    private val TAG = javaClass.name
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val viewModel: PlantRegisterViewModel by activityViewModels()
    private lateinit var binding: FragmentPlantAirBinding
    private var isChecking: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentPlantAirBinding.inflate(layoutInflater)

        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navController = this.findNavController()
        initObservers()

        binding.plantAirBackBtn.setOnClickListener {
            navController.popBackStack()
        }
        buttonCheck()
        binding.airNextBtn.setOnClickListener {
            if(!isChecking){
                ErrorToast(requireContext()).showMsgDown(resources.getString(R.string.plant_air_fragment_error),binding.airThreeBtn.y+binding.airThreeBtn.height)
            }else{
                viewModel.postPlant()

                // logging
                val loggingBundle = Bundle()
                loggingBundle.putString(CLASS_NAME, TAG)
                firebaseAnalytics.logEvent(PLANT_REGISTER_CLICK , loggingBundle)
            }
        }
        return binding.root
    }

    fun buttonCheck(){
        /* button selector */
        binding.airOneBtn.setOnClickListener {
            viewModel.setAir(AirType.YES.apiString)
            isChecking = true
            binding.airOneBtn.isSelected = true
            binding.airTwoBtn.isSelected = false
            binding.airThreeBtn.isSelected = false
        }
        binding.airTwoBtn.setOnClickListener {
            isChecking = true
            viewModel.setAir(AirType.WINDOW.apiString)
            binding.airOneBtn.isSelected = false
            binding.airTwoBtn.isSelected = true
            binding.airThreeBtn.isSelected = false
        }
        binding.airThreeBtn.setOnClickListener {
            isChecking = true
            viewModel.setAir(AirType.NO.apiString)
            binding.airOneBtn.isSelected = false
            binding.airTwoBtn.isSelected = false
            binding.airThreeBtn.isSelected = true

        }
    }

    /* bright, half_bright, lamp, dark */
//        binding.airOne.text = resources.getString(LightType.BRIGHT.descId)
//        binding.airTwo.text = resources.getString(LightType.HALF_BRIGHT.descId)
//        binding.airThree.text = resources.getString(LightType.LAMP.descId)
//        binding.airFour.text = resources.getString(LightType.DARK.descId)

    private fun initObservers(){
        viewModel.plantRegisterSuccess.observe(viewLifecycleOwner, Observer{
            if(it){
                activity?.finish()
            }else{
                ErrorToast(requireContext()).showMsgDown(resources.getString(R.string.post_plant_error),binding.airThreeBtn.y+binding.airThreeBtn.height)
            }
        })
    }
}


