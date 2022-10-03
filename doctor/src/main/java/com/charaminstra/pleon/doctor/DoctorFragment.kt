package com.charaminstra.pleon.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common.CLASS_NAME
import com.charaminstra.pleon.common.DOCTOR_VIEW
import com.charaminstra.pleon.doctor.databinding.FragmentDoctorBinding
import com.google.firebase.analytics.FirebaseAnalytics

class DoctorFragment : Fragment() {

    private val TAG = javaClass.name
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var binding : FragmentDoctorBinding

    private lateinit var viewModel: DoctorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())
        // logging
        val bundle = Bundle()
        bundle.putString(CLASS_NAME, TAG)
        firebaseAnalytics.logEvent(DOCTOR_VIEW , bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navController = this.findNavController()
        binding = FragmentDoctorBinding.inflate(inflater, container, false)
        binding.doctorNextBtn.setOnClickListener {
            navController.navigate(R.id.doctor_fragment_to_q_plant_register_fragment)
        }
        return binding.root
    }

}