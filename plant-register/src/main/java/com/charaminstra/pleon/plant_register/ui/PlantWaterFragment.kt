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
import com.charaminstra.pleon.common_ui.PLeonMsgDialog
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantWaterBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class PlantWaterFragment : Fragment() {
    private val TAG = javaClass.name
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    private lateinit var binding: FragmentPlantWaterBinding
    private lateinit var dateFormat: SimpleDateFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantWaterBinding.inflate(layoutInflater)
        dateFormat = SimpleDateFormat(resources.getString(com.charaminstra.pleon.common_ui.R.string.date_view_format))
        val navController = this.findNavController()

        showKeyboard(binding.plantWaterEt,requireContext())
        binding.plantWaterEt.requestFocus()

        binding.plantWaterBackBtn.setOnClickListener {
            navController.popBackStack()
        }


        /* date picker */
        binding.plantWaterEt.setOnClickListener {
            binding.plantWaterDatePickerDialog.visibility=View.VISIBLE

            binding.root.setBackgroundColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.black_sub_color))
            binding.plantWaterBtns.visibility = View.GONE
        }
        binding.plantWaterDatePickerCancelBtn.setOnClickListener {
            binding.plantWaterDatePickerDialog.visibility=View.GONE

            binding.root.setBackgroundColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.white))
            binding.plantWaterBtns.visibility = View.VISIBLE
        }
        //확인버튼
        binding.plantWaterDatePickerOkBtn.setOnClickListener {
            binding.plantWaterEt.setText(DateUtils(requireContext()).datePickerToView(binding.plantWaterDatePicker.date))

            binding.plantWaterDatePickerDialog.visibility=View.GONE
            binding.root.setBackgroundColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.white))
            binding.plantWaterBtns.visibility = View.VISIBLE

            binding.root.setBackgroundColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.white))
            binding.plantWaterNextBtn.isClickable = true
        }

        binding.plantWaterNextBtn.setOnClickListener {
            if(binding.plantWaterEt.text.isNullOrBlank()){
                ErrorToast(requireContext()).showMsgDown(resources.getString(R.string.plant_water_fragment_error),binding.plantWaterEt.y)
            }else{
                viewModel.setWater_date(DateUtils(requireContext()).viewToSendServer(binding.plantWaterEt.text.toString()))
                navController.navigate((R.id.plant_water_fragment_to_plant_light_fragment))
            }
        }

        binding.plantWaterSkipBtn.setOnClickListener {
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