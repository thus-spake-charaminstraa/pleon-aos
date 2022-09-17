package com.charaminstra.pleon.garden

import com.bumptech.glide.Glide
import com.charaminstra.pleon.common.CommonViewHolder
import com.charaminstra.pleon.foundation.model.PlantDataObject
import com.charaminstra.pleon.garden.databinding.ItemGardenPlantBinding

class GardenPlantViewHolder(
    private val binding: ItemGardenPlantBinding
): CommonViewHolder(binding) {

    override fun bind(item: PlantDataObject, onItemClicked: (String) -> Unit) {
        binding.plantName.text=item.name
        Glide.with(binding.root)
            .load(item.thumbnail)
            .into(binding.plantImage)
        binding.root.setOnClickListener {
            onItemClicked(item.id!!)
        }
    }

}