package com.charaminstra.pleon.feed.noti

import android.view.View
import androidx.navigation.findNavController
import com.charaminstra.pleon.feed.databinding.ItemNotiTextBinding
import com.charaminstra.pleon.common.data.NotiData


class NotiTextViewHolder (private val binding: ItemNotiTextBinding): NotiCommonViewHolder(binding) {
    init {
        binding.setClickListener {
            binding.noti?.feed_id?.let { feedId ->
                navigateToFeed(feedId, it)
            }
        }
    }

    private fun navigateToFeed(
        feedId: String,
        view: View
    ) {
        val direction =
            NotiFragmentDirections.actionNotiFragmentToFeedDetailFragment(
                feedId
            )
        view.findNavController().navigate(direction)
    }

    override fun bind(item: NotiData) {
        binding.apply {
            noti = item
            executePendingBindings()
        }
    }
}