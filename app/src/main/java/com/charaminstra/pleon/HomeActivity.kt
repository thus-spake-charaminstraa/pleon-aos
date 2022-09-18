package com.charaminstra.pleon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.charaminstra.pleon.databinding.ActivityHomeBinding
import com.charaminstra.pleon.garden.GardenFragment
import com.charaminstra.pleon.view.FeedFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.itemIconTintList = null

        binding.bottomNavigation.setOnItemReselectedListener {  item ->
            when (item.itemId) {
                R.id.nav_feed -> {

                }
                R.id.chat -> {

                }
                R.id.doctor -> {

                }
                R.id.nav_garden -> {
                    navController.navigate(R.id.nav_garden_init)
                }
                R.id.my -> {

                }
            }
        }
    }
}
