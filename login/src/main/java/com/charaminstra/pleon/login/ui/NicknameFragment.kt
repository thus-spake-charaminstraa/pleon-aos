package com.charaminstra.pleon.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common.*
import com.charaminstra.pleon.login.R
import com.charaminstra.pleon.login.UserCreateViewModel
import com.charaminstra.pleon.login.databinding.FragmentNicknameBinding
import com.charaminstra.pleon.login.startHomeActivity
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

const val FROM_LOGIN_TO_PLANT_REGISTER = 1001

@AndroidEntryPoint
class NicknameFragment : Fragment() {
    private val TAG = javaClass.simpleName
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private lateinit var binding : FragmentNicknameBinding
    private val viewModel: UserCreateViewModel by viewModels()
    private lateinit var navController: NavController

    lateinit var loginMethod : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNicknameBinding.inflate(layoutInflater)

        loginMethod = arguments?.getString("loginMethod")!!

        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())

        /* auto keyboard set*/
        showKeyboard(binding.nicknameEt,requireContext())
        binding.nicknameEt.requestFocus()

        initListeners()
        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = this.findNavController()
        binding.nicknameBackBtn.setOnClickListener {
            navController.popBackStack()
        }
        return binding.root
    }

    private fun initObservers(){
        viewModel.userCreateSuccess.observe(this, Observer {
            if(it == true){
                startPlantRegisterActivityForResult(requireContext())
            }
        })
    }

    private fun initListeners(){
        binding.nicknameRegisterBtn.setOnClickListener {

            // logging
            val loggingBundle = Bundle()
            loggingBundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(ACCOUNT_REGISTER_CLICK+loginMethod,loggingBundle)

            if(loginMethod == "phone"){
                viewModel.userPhoneCreate(binding.nicknameEt.text.toString())

            }else if(loginMethod == "kakao"){
                viewModel.userKakaoCreate(binding.nicknameEt.text.toString())
            }
        }
    }

    fun startPlantRegisterActivityForResult(context: Context) {
        val intent = Intent(
            context,
            Class.forName("com.charaminstra.pleon.plant_register.ui.PlantRegisterActivity")
        )
        startActivityForResult(intent, FROM_LOGIN_TO_PLANT_REGISTER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            FROM_LOGIN_TO_PLANT_REGISTER -> {
                navController.navigate(R.id.nickname_fragment_to_welcome_fragment)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard(binding.nicknameEt)
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0);
    }
}
