package com.charaminstra.pleon.login.ui

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.R
import com.charaminstra.pleon.login.SmsViewModel
import com.charaminstra.pleon.login.ui.LoginActivity.Companion.prefs
import com.charaminstra.pleon.login.databinding.FragmentPhoneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneFragment : Fragment() {
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
//        viewModel.phoneResponse.observe(this, Observer {
//            it.let{
//                Log.d("PhoneResponse", it.toString())
//                if(it.success == true){
//                    Toast.makeText(context,"인증번호가 문자로 전송되었습니다.",Toast.LENGTH_SHORT)
//                }
//            }
//        })
//        viewModel.liveData.observe(this, Observer {
//            it?.let{
//                prefs.setVerifyToken(it.verify_token.toString())
//                if(it.isExist == true){
//                    /* 기존 회원 */
//                    startHomeActivity()
//                }else{
//                    /* 신규 회원 */
//                    navController.navigate(R.id.phone_fragment_to_nickname_fragment)
//                }
//            }
//        })
    }
    private fun initListeners(){
        binding.phoneBtn.setOnClickListener {
            var phone = binding.phoneEt.text.toString()
            binding.codeEt.visibility = View.VISIBLE
            binding.checkBtn.visibility = View.VISIBLE
            viewModel.postPhoneNum(phone)
            it.isClickable = false
        }
        binding.checkBtn.setOnClickListener {
            var phone = binding.phoneEt.text.toString()
            var code = binding.codeEt.text.toString()
            viewModel.postCode(phone,code)
            /* temp */
            navController.navigate(R.id.phone_fragment_to_nickname_fragment)
        }
    }

    private fun startHomeActivity() {
        val intent = Intent(
            requireContext(),
            Class.forName("com.charaminstra.pleon.HomeActivity")
        )
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}