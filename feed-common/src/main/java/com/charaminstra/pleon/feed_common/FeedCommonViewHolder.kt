package com.charaminstra.pleon.feed_common

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.charaminstra.pleon.common.FeedViewObject

abstract class FeedCommonViewHolder(
    binding: ViewBinding
): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(viewObject: FeedViewObject?, fromView: String)
}