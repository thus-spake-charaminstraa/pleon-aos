package com.charaminstra.pleon

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charaminstra.pleon.viewmodel.FeedViewModel

class FeedDetailFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    lateinit var feedId : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*plant Id*/
        arguments?.getString("id")?.let {
            feedId = it
            //viewModel.loadData(feedId)
        }
        return inflater.inflate(R.layout.fragment_feed_detail, container, false)
    }


}