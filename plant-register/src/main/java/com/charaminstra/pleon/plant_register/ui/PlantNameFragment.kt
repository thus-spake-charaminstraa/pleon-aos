package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common.showKeyboard
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.common_ui.PLeonMsgDialog
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantNameFragment : Fragment() {
    private val TAG = javaClass.name
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    private lateinit var binding: FragmentPlantNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantNameBinding.inflate(layoutInflater)
        /* auto keyboard set*/
        showKeyboard(binding.plantNameEt,requireContext())
        binding.plantNameEt.requestFocus()

        val navController = this.findNavController()

        binding.plantNameBackBtn.setOnClickListener {
            navController.popBackStack()
        }

        binding.plantNameNextBtn.setOnClickListener {
            //test
            //navController.navigate(R.id.plant_name_fragment_to_plant_adopt_fragment)
            if(binding.plantNameEt.text.isNullOrBlank()){
                ErrorToast(requireContext()).showMsg(resources.getString(R.string.plant_name_fragment_error),binding.plantNameEt.y)
            }else{
                viewModel.setName(binding.plantNameEt.text.toString())
                navController.navigate(R.id.plant_name_fragment_to_plant_adopt_fragment)
            }
        }

        binding.plantNameSkipBtn.setOnClickListener {
            val dlg = PLeonMsgDialog(requireContext())
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

        return binding.root
    }

}