package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common_ui.CustomDialog
import com.charaminstra.pleon.common_ui.showErrorToast
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantThumbnailBinding

class PlantThumbnailFragment : Fragment() {
    private lateinit var binding: FragmentPlantThumbnailBinding
    private val viewModel: PlantRegisterViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantThumbnailBinding.inflate(layoutInflater)
        val navController = this.findNavController()

        binding.plantRegisterSkipBtn.setOnClickListener {
            val dlg = CustomDialog(requireContext())
            dlg.setOnOKClickedListener {
                activity?.finish()
            }
            dlg.start(
                resources.getString(R.string.plant_register_skip_dialog_title),
                resources.getString(R.string.plant_register_skip_dialog_desc),
                resources.getString(R.string.plant_register_skip_dialog_cancel_btn),
                resources.getString(R.string.plant_register_skip_dialog_skip_btn)
            )
        }

        binding.plantRegisterNextBtn.setOnClickListener {
            navController.navigate(R.id.plant_thumbnail_fragment_to_plant_species_fragment)
            if(viewModel.urlResponse.value.isNullOrBlank()){
                Toast(activity).showErrorToast(resources.getString(R.string.plant_thumbnail_fragment_error),binding.plantThumbnailAddImg.y,requireActivity())
            }else{
                //test
                //navController.navigate(R.id.plant_register_fragment_to_plant_light_fragment)
            }
        }

        return binding.root
    }
}