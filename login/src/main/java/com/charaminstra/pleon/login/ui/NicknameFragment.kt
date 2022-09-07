package com.charaminstra.pleon.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.R
import com.charaminstra.pleon.login.UserCreateViewModel
import com.charaminstra.pleon.login.databinding.FragmentNicknameBinding
import com.charaminstra.pleon.login.showKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NicknameFragment : Fragment() {
    private val TAG = javaClass.simpleName
    private lateinit var binding : FragmentNicknameBinding
    private val viewModel: UserCreateViewModel by viewModels()
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNicknameBinding.inflate(layoutInflater)
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
                startPlantRegisterActivity(requireContext())
            }
        })
    }

    private fun initListeners(){
        binding.nicknameRegisterBtn.setOnClickListener {
            viewModel.userCreate(binding.nicknameEt.text.toString())
        }
    }

    fun startPlantRegisterActivity(context: Context) {
        val intent = Intent(
            context,
            Class.forName("com.charaminstra.pleon.plant_register.ui.PlantRegisterActivity")
        )
        intent.putExtra("from","login")
        ContextCompat.startActivity(context, intent, null)
        navController.navigate(R.id.nickname_fragment_to_welcome_fragment)
    }
}
