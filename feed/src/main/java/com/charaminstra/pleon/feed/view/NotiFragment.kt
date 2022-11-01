package com.charaminstra.pleon.feed.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charaminstra.pleon.feed.databinding.FragmentNotiBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotiFragment : Fragment() {

    private lateinit var binding: FragmentNotiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotiBinding.inflate(layoutInflater)

        //val adapter = NotiAdapter()

        return binding.root
    }

}