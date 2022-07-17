package com.charaminstra.pleon.login.ui

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.R
import com.charaminstra.pleon.login.SmsViewModel
import com.charaminstra.pleon.login.databinding.FragmentPhoneBinding
import com.charaminstra.pleon.login.startHomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneFragment : Fragment() {
    private val TAG = javaClass.simpleName
    private lateinit var binding: FragmentPhoneBinding
    private val viewModel: SmsViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPhoneBinding.inflate(layoutInflater)
        initListeners()
        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* 뒤로가기 */
        navController = this.findNavController()
        binding.backBtn.setOnClickListener{
            navController.popBackStack()
        }
        /* 자동 하이픈 */
        binding.phoneEt.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        return binding.root
    }

    private fun initObservers(){
        viewModel.phoneResponse.observe(this, Observer {
            if(it){
                binding.codeEt.visibility = View.VISIBLE
                binding.checkBtn.visibility = View.VISIBLE
                binding.phoneBtn.isClickable = false
            }
        })
        viewModel.codeResponse.observe(this, Observer {
            Log.i(TAG,"code response : $it")
            if(it){
                viewModel.userExist.observe(this, Observer {
                    Log.i(TAG,"user exist : $it")
                    if(it){
                        /* 기존 회원 */
                        viewModel.postLogin()
                        startHomeActivity(requireContext())
                    }else{
                        /* 신규 회원 */
                        navController.navigate(R.id.phone_fragment_to_nickname_fragment)

                    }
                })
            }
        })
    }
    private fun initListeners(){
        binding.phoneBtn.setOnClickListener {
            var phone = binding.phoneEt.text.toString()
            viewModel.postPhoneNum(phone)

        }
        binding.checkBtn.setOnClickListener {
            var phone = binding.phoneEt.text.toString()
            var code = binding.codeEt.text.toString()
            viewModel.postCode(phone,code)
            /* temp */
            //navController.navigate(R.id.phone_fragment_to_nickname_fragment)
        }
    }
}