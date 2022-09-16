package com.charaminstra.pleon.feed

import android.graphics.Bitmap
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.charaminstra.pleon.common.CommonViewHolder
import com.charaminstra.pleon.feed.databinding.ItemPlantFilterBinding
import com.charaminstra.pleon.foundation.model.PlantDataObject


class FeedPlantViewHolder(private val binding: ItemPlantFilterBinding): CommonViewHolder(binding) {
    override fun bind(item: PlantDataObject, onItemClicked: (String) -> Unit) {
        binding.plantName.text=item.name

        Glide.with(binding.plantImage.context)
            .load(item.thumbnail)
            .circleCrop()
            .into(binding.plantImage)
        binding.plantFilterRoot.setOnClickListener {
            Log.i("ha plant","click click")
            onItemClicked(item.id!!)
        }
    }
}

