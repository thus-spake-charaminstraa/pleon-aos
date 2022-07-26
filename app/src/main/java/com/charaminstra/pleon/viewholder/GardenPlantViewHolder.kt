package com.charaminstra.pleon.viewholder

import com.charaminstra.pleon.databinding.ItemPlantGardenBinding
import com.charaminstra.pleon.foundation.model.PlantDataObject

class GardenPlantViewHolder(private val binding: ItemPlantGardenBinding): PlantViewHolder(binding) {
    override fun bind(item: PlantDataObject) {
        binding.nameTv.text=item.name
    }
}