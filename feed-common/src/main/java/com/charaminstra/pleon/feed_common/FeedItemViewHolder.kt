package com.charaminstra.pleon.feed_common

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.charaminstra.pleon.common.CLASS_NAME
import com.charaminstra.pleon.common.FEED_ITEM_CLICK
import com.charaminstra.pleon.feed_common.databinding.ItemFeedBinding
import com.charaminstra.pleon.common.FeedViewObject
import com.charaminstra.pleon.feed.view.FeedFragmentDirections
import com.google.firebase.analytics.FirebaseAnalytics

class FeedItemViewHolder(
    private val binding: ItemFeedBinding
): FeedCommonViewHolder(binding){
    private val TAG = javaClass.name
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    init {
        binding.setClickListener {
            binding.feed?.id?.let { feedId ->
                navigateToFeedDetail(feedId, it)
            }
        }
    }

    private fun navigateToFeedDetail(
        feedId: String,
        view: View
    ) {
        val direction =
            FeedFragmentDirections.feedFragmentToFeedDetailFragment(
                feedId
            )
        view.findNavController().navigate(direction)
    }

    override fun bind(item: FeedViewObject?)  {
        binding.apply {
            feed = item
            executePendingBindings()
        }
        if(item?.image_url != null) {
            binding.plantImage.visibility = View.VISIBLE
        }else{
            binding.plantImage.visibility = View.GONE
        }

        //comment
        val count = item?.comments?.size
        if(count == 0) {
            binding.icComment.visibility = View.GONE
            binding.feedCommentCount.visibility = View.GONE
        }else{
            binding.icComment.visibility = View.VISIBLE
            binding.feedCommentCount.visibility = View.VISIBLE
            binding.feedCommentCount.text = count.toString()
        }
    }
}
