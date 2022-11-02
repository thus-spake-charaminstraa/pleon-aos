package com.charaminstra.pleon.feed_common

import android.view.View
import androidx.navigation.findNavController
import com.charaminstra.pleon.feed_common.databinding.ItemFeedBinding
import com.charaminstra.pleon.common.FeedViewObject
import com.charaminstra.pleon.feed.view.FeedFragmentDirections
import java.text.SimpleDateFormat

class FeedItemViewHolder(
    private val binding: ItemFeedBinding
): FeedCommonViewHolder(binding){
    private lateinit var dateFormat: SimpleDateFormat
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

    override fun bind(item: FeedViewObject?, viewType:Int, onClickFeed: (Int, String) -> Unit)  {
        binding.apply {
            feed = item
            executePendingBindings()
        }

        dateFormat = SimpleDateFormat(binding.root.context.resources.getString(com.charaminstra.pleon.common.R.string.date_view_format))

        if(item?.image_url != null) {
            binding.plantImage.visibility = View.VISIBLE
        }else{
            binding.plantImage.visibility = View.GONE
        }
        binding.feedDate.text = dateFormat.format(item?.publish_date)
        binding.feedDate.text = dateFormat.format(item?.publish_date)


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
