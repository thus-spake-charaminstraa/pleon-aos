package com.charaminstra.pleon.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.databinding.ItemFeedBinding
import com.charaminstra.pleon.databinding.ItemPlantGardenBinding
import com.charaminstra.pleon.foundation.model.FeedDataObject

class FeedViewHolder(
    private val binding: ItemFeedBinding,
    private var onItemClicked: (String) -> Unit): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FeedDataObject) {
        binding.feedContent.text = item.content
        binding.plantTagTv.text = "@"+item.plant_id
        binding.actionTagTv.text = "#"+item.kind
    }

}