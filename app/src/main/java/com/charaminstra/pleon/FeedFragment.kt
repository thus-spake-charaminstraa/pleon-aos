package com.charaminstra.pleon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.charaminstra.pleon.adapter.CommonAdapter
import com.charaminstra.pleon.databinding.FragmentFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private val viewModel: PlantsViewModel by viewModels()
    private lateinit var binding : FragmentFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFeedBinding.inflate(layoutInflater)
        viewModel.getData().observe(this, Observer {
            Log.i("list",it.toString())
            val adapter = CommonAdapter(it,"FEED_PLANT")
            binding.filterRecyclerview.adapter = adapter
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }
}