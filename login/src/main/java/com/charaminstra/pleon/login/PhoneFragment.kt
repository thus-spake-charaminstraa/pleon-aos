package com.charaminstra.pleon.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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


        viewModel.getData().observe(this) { data ->
            data?.let {
                Log.i("PhoneFragment", "success it?"+it)
            }
        }


        binding.checkBtn.setOnClickListener {
            val phone = "01011112222"
            val code = "777777"
//            viewModel.login(phone,code)
//            viewModel.liveData.observe(this, {
//                Log.d("PhoneFragment", it.toString())
//            })

        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPhoneBinding.inflate(inflater, container, false)
        val navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
        binding.checkBtn.setOnClickListener {
            val phone = "01011112222"
            val code = "777777"
            navController.navigate(R.id.phone_fragment_to_nickname_fragment)
        }

        return binding.root
    }

}