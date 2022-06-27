package com.charaminstra.pleon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charaminstra.pleon.databinding.FragmentCameraBinding


class CameraFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }
}