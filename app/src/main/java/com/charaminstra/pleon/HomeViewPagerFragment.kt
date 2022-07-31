package com.charaminstra.pleon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.R
import com.charaminstra.pleon.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager
        viewPager.adapter = PleonPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
        }.attach()

//        prefs = com.charaminstra.pleon.common.PreferenceUtil(requireContext())
//        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
//        if(prefs.getString("token","").isEmpty()) {
//            navController.navigate(R.id.action_view_pager_fragment_to_login_graph)
//        }

        return binding.root
    }
    private fun getTabIcon(position: Int): Int {
        return when (position) {
            FEED_PAGE_INDEX -> R.drawable.feed_tab_selector
            CAMERA_PAGE_INDEX -> R.drawable.camera_tab_selector
            GARDEN_PAGE_INDEX -> R.drawable.garden_tab_selector
            MY_PAGE_INDEX -> R.drawable.my_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }
}

