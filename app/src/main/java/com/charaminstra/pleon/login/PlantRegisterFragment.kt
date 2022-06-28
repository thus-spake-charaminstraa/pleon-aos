package com.charaminstra.pleon.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.charaminstra.pleon.FeedFragment.Companion.prefs
import com.charaminstra.pleon.R
import com.charaminstra.pleon.databinding.FragmentPlantRegisterBinding

class PlantRegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlantRegisterBinding.inflate(inflater, container, false)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
        binding.backBtn.setOnClickListener {
            navController.navigate(R.id.nickname_fragment)
        }
//        binding.checkBtn.setOnClickListener {
//            navController.navigate(R.id.nickname_fragment)
//        }
        binding.skipBtn.setOnClickListener {
            prefs.setString("phone", "010-1234-1234")
            prefs.setString("token", "012341234")
            navController.navigate(R.id.view_pager_fragment)
        }
        return binding.root
    }
}