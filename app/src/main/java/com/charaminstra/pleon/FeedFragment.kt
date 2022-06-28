package com.charaminstra.pleon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import com.charaminstra.pleon.FeedFragment.Companion.prefs
import com.charaminstra.pleon.databinding.FragmentFeedBinding
import com.charaminstra.pleon.login.LoginFragment

class FeedFragment : Fragment() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)
        prefs = PreferenceUtil(requireContext())
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
//        prefs.delete("phone")
//        prefs.delete("token")
//        prefs.setString("phone", "010-1234-1234")
//        prefs.setString("token", "012341234")
        Log.i("phone", prefs.getString("phone",""))
        Log.i("token", prefs.getString("token",""))
        if(prefs.getString("phone","").isEmpty() || prefs.getString("token","").isEmpty()) {
            navController.navigate(R.id.login_fragment)
        }
        return binding.root
    }
}