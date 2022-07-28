package com.charaminstra.pleon.viewholder

import com.bumptech.glide.Glide
import com.charaminstra.pleon.databinding.ItemPlantGardenBinding
import com.charaminstra.pleon.foundation.model.PlantDataObject

class GardenPlantViewHolder(
    private val binding: ItemPlantGardenBinding,
    private var onItemClicked: (String) -> Unit): CommonViewHolder(binding) {

    override fun bind(item: PlantDataObject) {
        binding.plantName.text=item.name
        Glide.with(binding.root)
            .load(item.thumbnail)
            .into(binding.plantImage)

        binding.root.setOnClickListener {
            onItemClicked(item.id!!)
        }
    }

}