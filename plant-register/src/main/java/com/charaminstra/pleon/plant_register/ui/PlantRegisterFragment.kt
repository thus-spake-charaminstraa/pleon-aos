package com.charaminstra.pleon.plant_register.ui

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PlantRegisterFragment : Fragment() {
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    private lateinit var binding: FragmentPlantRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantRegisterBinding.inflate(inflater, container, false)
        val navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            activity?.finish()
        }
        binding.adoptDayInput.setOnClickListener {
            popUpCalendar(it as TextView)
        }
        binding.waterDayInput.setOnClickListener {
            popUpCalendar(it as TextView)
        }

//        binding.skipBtn.setOnClickListener {
//            startHomeActivity()
//        }
        binding.nextBtn.setOnClickListener {
            viewModel.setName(binding.nameInput.text.toString())
            viewModel.setSpecies(binding.speciesInput.text.toString())
            viewModel.setAdopt_date(binding.adoptDayInput.text.toString())
            viewModel.setWater_date(binding.waterDayInput.text.toString())
            navController.navigate(R.id.plant_register_fragment_to_plant_light_fragment)
        }
//        binding.cameraBtn.setOnClickListener {
//        }

        return binding.root
    }

    fun popUpCalendar(view: TextView) {
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        var datePickerDialog = DatePickerDialog(requireContext(), { _, y, m, d ->
            view.text = dateFormat.format(cal.time)
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.BLACK)
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackgroundColor(Color.BLACK)
    }
}