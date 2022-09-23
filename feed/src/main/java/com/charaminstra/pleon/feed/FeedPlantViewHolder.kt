package com.charaminstra.pleon.feed

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.common.PlantCommonViewHolder
import com.charaminstra.pleon.feed.databinding.ItemPlantBinding
import com.charaminstra.pleon.foundation.model.PlantDataObject

class FeedPlantViewHolder(val binding: ItemPlantBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(item: PlantDataObject) {
        binding.plantName.text=item.name
        Glide.with(binding.plantImg.context)
            .load(item.thumbnail)
            .circleCrop()
            .into(binding.plantImg)
    }
}

