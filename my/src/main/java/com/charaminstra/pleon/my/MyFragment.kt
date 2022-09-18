package com.charaminstra.pleon.my

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.my.databinding.FragmentMyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFragment : Fragment() {
    private val TAG = javaClass.name
    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding : FragmentMyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBinding.inflate(inflater, container, false)
        val navController = this.findNavController()
        binding.editBtn.setOnClickListener {
            navController.navigate(R.id.my_fragment_to_my_edit_fragment)
        }
        binding.logoutBtn.setOnClickListener{
            viewModel.deletePreference()
            val intent = Intent(
                context,
                Class.forName("com.charaminstra.pleon.login.ui.LoginActivity")
            )
            startActivity(intent)
            activity?.finish()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
    }

    private fun initObservers(){
        viewModel.userName.observe(viewLifecycleOwner, Observer{
            if(it!=null){
                binding.userName.text = it
            }
        })
        viewModel.urlResponse.observe(viewLifecycleOwner, Observer{
            Glide.with(binding.root)
                .load(it)
                .into(binding.userImg)
            binding.userImg.setPadding(0)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserData()
    }
}








