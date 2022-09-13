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
import com.charaminstra.pleon.common_ui.DateUtils
import com.charaminstra.pleon.common_ui.PLeonMsgDialog
import com.charaminstra.pleon.common_ui.showErrorToast
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantAdoptBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class PlantAdoptFragment : Fragment() {
    private val TAG = javaClass.name
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    private lateinit var binding: FragmentPlantAdoptBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantAdoptBinding.inflate(layoutInflater)

        val navController = this.findNavController()

        showKeyboard(binding.plantAdoptEt,requireContext())
        binding.plantAdoptEt.requestFocus()

        binding.plantAdoptBackBtn.setOnClickListener {
            navController.popBackStack()
        }

        /* date picker */
        binding.plantAdoptEt.setOnClickListener {
            binding.plantAdoptDatePickerDialog.visibility=View.VISIBLE

            binding.root.setBackgroundColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.black_sub_color))
            binding.plantAdoptBtns.visibility = View.GONE
        }
        binding.plantAdoptDatePickerCancelBtn.setOnClickListener {
            binding.plantAdoptDatePickerDialog.visibility=View.GONE

            binding.root.setBackgroundColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.white))
            binding.plantAdoptBtns.visibility = View.VISIBLE
        }
        //확인 버튼
        binding.plantAdoptDatePickerOkBtn.setOnClickListener {
            binding.plantAdoptEt.setText(DateUtils(requireContext()).datePickerToView(binding.plantAdoptDatePicker.date))

            binding.plantAdoptDatePickerDialog.visibility=View.GONE
            binding.root.setBackgroundColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.white))
            binding.plantAdoptBtns.visibility = View.VISIBLE

            binding.root.setBackgroundColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.white))
            binding.plantAdoptNextBtn.isClickable = true
        }


        binding.plantAdoptNextBtn.setOnClickListener {
            //test
            //navController.navigate(R.id.plant_adopt_fragment_to_plant_water_fragment)
            if(binding.plantAdoptEt.text.isNullOrBlank()){
                Toast(activity).showErrorToast(resources.getString(R.string.plant_adopt_fragment_error),binding.plantAdoptEt.y,requireActivity())
            }else{
                viewModel.setWater_date(DateUtils(requireContext()).viewToSendServer(binding.plantAdoptEt.text.toString()))
                navController.navigate(R.id.plant_adopt_fragment_to_plant_water_fragment)
            }
        }

        binding.plantAdoptSkipBtn.setOnClickListener {
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