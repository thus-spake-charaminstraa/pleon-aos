package com.charaminstra.pleon.login.ui

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
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
import com.charaminstra.pleon.login.databinding.FragmentPhoneBinding
import com.charaminstra.pleon.common.showKeyboard
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.login.AuthViewModel
import com.charaminstra.pleon.login.startHomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneFragment : Fragment() {
    private val TAG = javaClass.simpleName
    private lateinit var binding: FragmentPhoneBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPhoneBinding.inflate(layoutInflater)
        /* auto keyboard set*/
        showKeyboard(binding.phoneEt,requireContext())
        binding.phoneEt.requestFocus()

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
        /* code */
        binding.codeEt.addTextChangedListener (object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s?.length == 6){
                    binding.phoneBtn.visibility = View.GONE
                    binding.codeBtn.visibility = View.VISIBLE
                }else{
                    binding.phoneBtn.visibility = View.VISIBLE
                    binding.codeBtn.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        return binding.root
    }

    private fun initObservers(){
        viewModel.phoneResponse.observe(this, Observer {
            if(it){

            }
        })
        viewModel.success.observe(this, Observer {
            Log.i(TAG,"code response : $it")
            if(it){

            }else{
                ErrorToast(requireContext()).showMsgDown(resources.getString(com.charaminstra.pleon.common_ui.R.string.code_error_msg),binding.codeEt.y)
                binding.codeEt.setTextColor(resources.getColor(com.charaminstra.pleon.common_ui.R.color.error_text_color))
            }
        })
        viewModel.userExist.observe(this, Observer {
            if(it){
                /* 기존 회원 */
                viewModel.postLogin()
            }else{
                /* 신규 회원 */
                val direction = PhoneFragmentDirections.phoneFragmentToNicknameFragment(
                    "phone"
                )
                navController.navigate(direction)
            }
        })
        viewModel.setTokenSuccess.observe(this, Observer {
            if(it){
                startHomeActivity(requireContext())
                activity?.finish()
            }
        })
    }
    private fun initListeners(){
        binding.phoneBtn.setOnClickListener {
            viewModel.setPhoneNum(binding.phoneEt.text.toString())
            viewModel.postPhoneNum()
            binding.phoneBtn.isClickable = false
            binding.phoneBtn.background = resources.getDrawable(com.charaminstra.pleon.common_ui.R.drawable.button_inactive)
            binding.phoneTitle.visibility = View.GONE
            binding.phoneEt.visibility = View.GONE
            binding.codeTitle.visibility = View.VISIBLE
            binding.codeEt.visibility = View.VISIBLE
            binding.codeEt.requestFocus()
            binding.helpTv.visibility = View.VISIBLE
        }
        binding.helpTv.setOnClickListener {
            binding.phoneBtn.visibility = View.VISIBLE
            binding.codeBtn.visibility = View.GONE
            binding.phoneBtn.isClickable = true
            binding.phoneBtn.background = resources.getDrawable(com.charaminstra.pleon.common_ui.R.drawable.button_main_green)
            binding.phoneBtn.text = getString(R.string.phone_fragment_resend)
        }
        binding.codeBtn.setOnClickListener {
            viewModel.postCode(binding.codeEt.text.toString())
        }
    }
}