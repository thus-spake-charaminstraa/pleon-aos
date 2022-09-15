package com.charaminstra.pleon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.charaminstra.pleon.databinding.FragmentViewPagerBinding

class HomeViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val viewPager = binding.viewPager
        viewPager.adapter = PleonPagerAdapter(this)
        viewPager.isUserInputEnabled = false

        binding.bottomNavigation.itemIconTintList = null

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}
            override fun onPageSelected(position: Int) {
                binding.bottomNavigation.menu.getItem(position).isChecked=true
            }
        })

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.feed -> {
                    binding.viewPager.currentItem = FEED_PAGE_INDEX
                }
                R.id.chat -> {
                    binding.viewPager.currentItem = CHAT_PAGE_INDEX
                }
                R.id.doctor -> {
                    binding.viewPager.currentItem = DOCTOR_PAGE_INDEX
                }
                R.id.garden -> {
                    binding.viewPager.currentItem = GARDEN_PAGE_INDEX
                }
                R.id.my -> {
                    binding.viewPager.currentItem = MY_PAGE_INDEX
                }
                else -> {
                    false
                }
            }
            true
        }
        return binding.root
    }
}

