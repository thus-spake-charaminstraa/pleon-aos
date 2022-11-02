package com.charaminstra.pleon.feed.noti

import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.feed.databinding.ItemNotiTextBinding
import com.charaminstra.pleon.feed.view.NotiFragmentDirections
import com.charaminstra.pleon.foundation.model.NotiData


class NotiTextViewHolder (private val binding: ItemNotiTextBinding): NotiCommonViewHolder(binding) {
//    init {
//        binding.setClickListener {
//            Log.i("youla","click")
//            binding.feedId?.let { feedId ->
//                navigateToFeed(feedId, it)
//            }
//        }
//    }
//
//    private fun navigateToFeed(
//        feedId: String,
//        view: View
//    ) {
//        val direction =
//            NotiFragmentDirections.actionNotiFragmentToFeedDetailFragment(
//                feedId
//            )
//        view.findNavController().navigate(direction)
//    }

    override fun bind(item: NotiData) {
        Glide.with(binding.root).load(item.plant?.thumbnail).circleCrop().into(binding.notiAuthorThumbnail)
        binding.notiTitle.text = item.content
        binding.commentContent.text = item.comment?.content
        binding.notiFeedPreview.text = item.feedContent
    }
}