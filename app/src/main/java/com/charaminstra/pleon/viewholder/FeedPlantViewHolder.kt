package com.charaminstra.pleon.viewholder

import com.charaminstra.pleon.databinding.ItemPlantFeedBinding
import com.charaminstra.pleon.foundation.model.PlantDataObject

class FeedPlantViewHolder(private val binding: ItemPlantFeedBinding): CommonViewHolder(binding) {
    override fun bind(item: PlantDataObject) {
        binding.nameTv.text=item.name
    }
}