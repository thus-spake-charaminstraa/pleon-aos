package com.charaminstra.pleon.plant_register.ui

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantDetectionResultBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Type
import kotlin.math.roundToInt

@AndroidEntryPoint
class PlantDetectionResultFragment : Fragment() {

    private val TAG = javaClass.name
    private lateinit var binding: FragmentPlantDetectionResultBinding
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    lateinit var navController : NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantDetectionResultBinding.inflate(layoutInflater)
        navController = this.findNavController()

        initObservers()
        Glide.with(requireContext()).load(viewModel.plantDetectionUrlResponse.value).into(binding.plantDetectionResultImg)

        binding.plantDetectionResultOkBtn.setOnClickListener {
            navController.navigate(R.id.plant_detection_result_return)
        }
        return binding.root
    }

    private fun initObservers(){
        viewModel.plantDetectionResultLabel.observe(viewLifecycleOwner, Observer{
            spannableSet(it + resources.getString(R.string.plant_detection_label_msg),it.length)
        })
        viewModel.plantDetectionResultPercent.observe(viewLifecycleOwner, Observer {
            binding.plantDetectionResultPercent.text =
                it.roundToInt().toString() + resources.getString(R.string.plant_detection_percent_msg)
        })
        viewModel.plantDetectionResultBenefit.observe(viewLifecycleOwner, Observer {
            binding.plantDetectionResultBenefitDesc.text = it
        })
        viewModel.plantDetectionResultTip.observe(viewLifecycleOwner, Observer {
            binding.plantDetectionResultTipDesc.text = it
        })
        viewModel.plantDetectionResultDifficulty.observe(viewLifecycleOwner, Observer {
            if(it == "easy"){
                binding.plantDetectionResultDifficultyEasy.background = resources.getDrawable(R.drawable.bg_difficulty_on)
                binding.plantDetectionResultDifficultyEasy.setTextColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.main_green_color))
                binding.plantDetectionResultDifficultyEasy.setTypeface(null,Typeface.BOLD)
            }else if(it == "normal"){
                binding.plantDetectionResultDifficultyNormal.background = resources.getDrawable(R.drawable.bg_difficulty_on)
                binding.plantDetectionResultDifficultyNormal.setTextColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.main_green_color))
                binding.plantDetectionResultDifficultyNormal.setTypeface(null,Typeface.BOLD)
            }else if(it == "hard"){
                binding.plantDetectionResultDifficultyHard.background = resources.getDrawable(R.drawable.bg_difficulty_on)
                binding.plantDetectionResultDifficultyHard.setTextColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.main_green_color))
                binding.plantDetectionResultDifficultyHard.setTypeface(null,Typeface.BOLD)
            }
        })
    }

    private fun spannableSet(msg: String, labelSize: Int){
        val plantDetectionLabelMsg  = SpannableString(msg)
        val foregroundSpan = ForegroundColorSpan(resources.getColor(com.charaminstra.pleon.common_ui.R.color.main_green_color))
        val styleSpan = StyleSpan(Typeface.BOLD)
        plantDetectionLabelMsg.setSpan(foregroundSpan,0,labelSize, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        plantDetectionLabelMsg.setSpan(styleSpan,0,labelSize, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.plantDetectionResultLabel.text = plantDetectionLabelMsg
    }
}