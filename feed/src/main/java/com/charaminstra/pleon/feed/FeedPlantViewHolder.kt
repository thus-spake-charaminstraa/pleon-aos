package com.charaminstra.pleon.feed

import com.bumptech.glide.Glide
import com.charaminstra.pleon.common.PlantCommonViewHolder
import com.charaminstra.pleon.feed.databinding.ItemPlantFilterBinding
import com.charaminstra.pleon.foundation.model.PlantDataObject


class FeedPlantViewHolder(private val binding: ItemPlantFilterBinding): PlantCommonViewHolder(binding) {
    override fun bind(item: PlantDataObject, onItemClicked: (String) -> Unit) {
        binding.plantName.text=item.name

        Glide.with(binding.plantImage.context)
            .load(item.thumbnail)
            .circleCrop()
            .into(binding.plantImage)
        binding.plantFilterRoot.setOnClickListener {
            it.isSelected = true
            onItemClicked(item.id!!)
        }
    }
}

