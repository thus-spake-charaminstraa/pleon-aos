package com.charaminstra.pleon.viewholder

import com.bumptech.glide.Glide
import com.charaminstra.pleon.databinding.ItemPlantFeedBinding
import com.charaminstra.pleon.foundation.model.PlantDataObject

class FeedPlantViewHolder(private val binding: ItemPlantFeedBinding): CommonViewHolder(binding) {
    override fun bind(item: PlantDataObject, onItemClicked: (String) -> Unit) {
        binding.plantName.text=item.name
        Glide.with(binding.root)
            .load(item.thumbnail)
            .circleCrop()
            .into(binding.plantImage)
        binding.plantImage.setOnClickListener {
            onItemClicked(item.id!!)
        }
    }
}