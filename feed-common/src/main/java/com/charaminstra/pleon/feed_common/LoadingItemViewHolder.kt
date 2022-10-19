package com.charaminstra.pleon.feed_common

import com.charaminstra.pleon.feed_common.databinding.ItemFeedLoadingBinding
import com.charaminstra.pleon.foundation.model.ViewObject

class LoadingItemViewHolder(
    private val binding: ItemFeedLoadingBinding
): FeedCommonViewHolder(binding){
    override fun bind(item: ViewObject?, viewType:Int, onClickFeed: (Int, String) -> Unit)  {

    }
}
