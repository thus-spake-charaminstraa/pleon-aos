package com.charaminstra.pleon

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.databinding.FragmentMyEditBinding
import com.charaminstra.pleon.databinding.FragmentPlantEditBinding
import com.charaminstra.pleon.foundation.api.DEFAULT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyEditFragment : Fragment() {
    private val TAG = javaClass.name
    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding : FragmentMyEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyEditBinding.inflate(inflater)
        val navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        viewModel.getUserData()
    }

    private fun initObservers(){
        viewModel.userName.observe(viewLifecycleOwner, Observer{
            if(it!=null){
                binding.userNameInput.setText(it)
            }
        })
        viewModel.userImgUrl.observe(viewLifecycleOwner, Observer{
            if(it == ""){
                Log.i(TAG, "userImgUrl is null $it")
                Glide.with(binding.root)
                    .load(R.drawable.ic_user)
                    .into(binding.editUserImage)

            }else{
                Log.i(TAG, "userImgUrl is not !! null $it")
                Glide.with(binding.root)
                    .load(it)
                    .into(binding.editUserImage)
            }
        })
    }

}