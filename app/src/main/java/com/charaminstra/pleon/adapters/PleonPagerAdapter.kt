package com.charaminstra.pleon.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.charaminstra.pleon.CameraFragment
import com.charaminstra.pleon.FeedFragment
import com.charaminstra.pleon.GardenFragment
import com.charaminstra.pleon.MyFragment

const val FEED_PAGE_INDEX = 0
const val CAMERA_PAGE_INDEX = 1
const val GARDEN_PAGE_INDEX = 2
const val MY_PAGE_INDEX = 3

class PleonPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        FEED_PAGE_INDEX to { FeedFragment() },
        CAMERA_PAGE_INDEX to { CameraFragment() },
        GARDEN_PAGE_INDEX to { GardenFragment() },
        MY_PAGE_INDEX to { MyFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}