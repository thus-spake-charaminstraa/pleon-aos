package com.charaminstra.pleon.garden

import com.bumptech.glide.Glide
import com.charaminstra.pleon.feed_common.PlantCommonViewHolder
import com.charaminstra.pleon.common.data.PlantDataObject
import com.charaminstra.pleon.garden.databinding.ItemGardenPlantBinding

class GardenPlantViewHolder(
    private val binding: ItemGardenPlantBinding
): PlantCommonViewHolder(binding) {

    override fun bind(item: PlantDataObject, onItemClicked: (String) -> Unit) {
        binding.plantName.text=item.name
        Glide.with(binding.root)
            .load(item.thumbnail)
            .into(binding.plantImage)
        binding.root.setOnClickListener {
            onItemClicked(item.id!!)
        }
        Glide.with(binding.root)
            .load(item.mood?.icon_uri)
            .into(binding.gardenMoodIcon)
        binding.gardenMoodTv.text=item.mood?.mood
    }
}