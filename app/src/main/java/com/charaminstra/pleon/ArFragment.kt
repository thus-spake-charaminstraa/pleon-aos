package com.charaminstra.pleon

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charaminstra.pleon.plant_ar.MainActivity
import com.charaminstra.pleon.databinding.FragmentArBinding

class ArFragment : Fragment() {
    private var _binding: FragmentArBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(getActivity(), MainActivity::class.java)
        startActivity(intent)
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentArBinding.inflate(inflater, container, false)
//        return binding.root
//
//    }

}