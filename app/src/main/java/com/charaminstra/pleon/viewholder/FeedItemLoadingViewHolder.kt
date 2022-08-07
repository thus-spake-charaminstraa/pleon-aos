package com.charaminstra.pleon.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.databinding.ItemFeedBinding
import com.charaminstra.pleon.databinding.ItemLoadingBinding
import com.charaminstra.pleon.foundation.model.ResultObject

class FeedItemLoadingViewHolder(private val binding: ItemLoadingBinding, private var onItemClicked: (String) -> Unit): FeedItemCommonViewHolder(binding) {
    override fun bind(item: ResultObject) {

    }
}