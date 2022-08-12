package com.charaminstra.pleon

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.charaminstra.pleon.databinding.FragmentMyBinding
import com.charaminstra.pleon.databinding.FragmentMyEditBinding
import com.charaminstra.pleon.foundation.api.DEFAULT
import com.charaminstra.pleon.login.ui.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFragment : Fragment() {
    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding : FragmentMyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBinding.inflate(inflater, container, false)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
        binding.editBtn.setOnClickListener {
            navController.navigate(R.id.view_pager_fragment_to_my_edit_fragment)
        }
        binding.logoutBtn.setOnClickListener{
            activity?.finish()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        viewModel.getUserName()
        viewModel.getUserImgUrl()
    }

    private fun initObservers(){
        viewModel.userName.observe(viewLifecycleOwner, Observer{
            if(it!=null){
                binding.userName.text = it
            }
        })
        viewModel.userImgUrl.observe(viewLifecycleOwner, Observer{
            if(it == DEFAULT){
                Glide.with(binding.root)
                    .load(R.drawable.ic_user)
                    .into(binding.userImg)

            }else{
                Glide.with(binding.root)
                    .load(it)
                    .into(binding.userImg)
            }
        })
    }
}








