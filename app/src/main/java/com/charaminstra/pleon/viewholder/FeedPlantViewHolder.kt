package com.charaminstra.pleon.viewholder

import android.graphics.Color
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.Resource
import com.charaminstra.pleon.R
import com.charaminstra.pleon.databinding.ItemPlantFeedBinding
import com.charaminstra.pleon.foundation.model.PlantDataObject

class FeedPlantViewHolder(private val binding: ItemPlantFeedBinding,
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