package com.charaminstra.pleon.feed_common

import com.charaminstra.pleon.feed_common.databinding.ItemFeedLoadingBinding
import com.charaminstra.pleon.common.FeedViewObject

class LoadingItemViewHolder(
    private val binding: ItemFeedLoadingBinding
): FeedCommonViewHolder(binding){
    override fun bind(item: FeedViewObject?, viewType:Int, onClickFeed: (Int, String) -> Unit)  {

    }
}
