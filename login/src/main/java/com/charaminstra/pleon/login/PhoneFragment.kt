package com.charaminstra.pleon.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.databinding.FragmentPhoneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneFragment : Fragment() {
    private lateinit var binding: FragmentPhoneBinding
    private val viewModel: TestViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPhoneBinding.inflate(layoutInflater)
        initListners()
        initObservers()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    private fun initObservers(){
        viewModel.liveData.observe(this, Observer {
            it?.let{
                Log.d("PhoneFragment", it.toString())
            }
        })
    }
    private fun initListners(){
        binding.checkBtn.setOnClickListener {
            viewModel.login(
                "01011112222","777777"
            )
        }
    }

}