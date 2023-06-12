package com.charaminstra.pleon.doctor

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charaminstra.pleon.common.CLASS_NAME
import com.charaminstra.pleon.common.DOCTOR_START_BTN_CLICK
import com.charaminstra.pleon.common.DOCTOR_VIEW
import com.charaminstra.pleon.doctor.databinding.FragmentDoctorBinding
import com.charaminstra.pleon.doctor.view.DoctorActivity
import com.google.firebase.analytics.FirebaseAnalytics

class DoctorFragment : Fragment() {

    private val TAG = javaClass.name
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var binding : FragmentDoctorBinding

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
        binding = FragmentDoctorBinding.inflate(inflater, container, false)
        binding.doctorNextBtn.setOnClickListener {
            // logging
            val bundle = Bundle()
            bundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(DOCTOR_START_BTN_CLICK , bundle)

            val intent = Intent(requireContext(), DoctorActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}