package com.charaminstra.pleon.feed

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.feed.databinding.ItemPlantBinding
import com.charaminstra.pleon.domain.model.Plant

class FeedPlantViewHolder(val binding: ItemPlantBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Plant) {
        binding.plantName.text=item.plantName
        Glide.with(binding.plantImg.context)
            .load(item.plantImageUrl)
            .circleCrop()
            .into(binding.plantImg)
    }
}

