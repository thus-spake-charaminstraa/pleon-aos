package com.charaminstra.pleon.garden

import com.bumptech.glide.Glide
import com.charaminstra.pleon.feed_common.PlantCommonViewHolder
import com.charaminstra.pleon.domain.model.Plant
import com.charaminstra.pleon.garden.databinding.ItemGardenPlantBinding

class GardenPlantViewHolder(
    private val binding: ItemGardenPlantBinding
): PlantCommonViewHolder(binding) {

    override fun bind(item: Plant, onItemClicked: (String) -> Unit) {
        binding.plantName.text=item.plantName
        Glide.with(binding.root)
            .load(item.plantImageUrl)
            .into(binding.plantImage)
        binding.root.setOnClickListener {
            onItemClicked(item.plantId!!)
        }
        Glide.with(binding.root)
            .load(item.plantMoodUri)
            .into(binding.gardenMoodIcon)
        binding.gardenMoodTv.text=item.plantMood
    }
}