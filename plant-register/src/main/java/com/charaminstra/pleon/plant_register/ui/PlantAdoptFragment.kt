package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common.showKeyboard
import com.charaminstra.pleon.common_ui.DateUtils
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.common_ui.PLeonDatePicker
import com.charaminstra.pleon.common_ui.PLeonMsgDialog
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantAdoptBinding
import dagger.hilt.android.AndroidEntryPoint

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
            val dlg= PLeonDatePicker(requireContext())
            dlg.setOnOKClickedListener {
                binding.plantAdoptEt.setText(dlg.date)
            }
            dlg.start(resources.getString(com.charaminstra.pleon.common_ui.R.string.date_picker_title))
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
            if(binding.plantAdoptEt.text.isNullOrBlank()){
                ErrorToast(requireContext()).showMsgDown(resources.getString(R.string.plant_adopt_fragment_error),binding.plantAdoptEt.y)
            }else{
                viewModel.setAdopt_date(DateUtils(requireContext()).viewToSendServer(binding.plantAdoptEt.text.toString()))
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