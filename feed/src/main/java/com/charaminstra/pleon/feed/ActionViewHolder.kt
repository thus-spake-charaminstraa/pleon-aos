package com.charaminstra.pleon.feed

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.common.ActionObject
import com.charaminstra.pleon.common.ActionType
import com.charaminstra.pleon.feed.databinding.ItemPlantFilterBinding

class ActionViewHolder(private val binding: ItemPlantFilterBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ActionObject, onItemClicked: (ActionType) -> Unit) {
        binding.plantName.text = item.actionType.toString()
        binding.plantImage.setOnClickListener {
            Log.i("ha action","click click")
            onItemClicked(item.actionType)
        }
        Glide.with(binding.root).load(item.actionImage).circleCrop().into(binding.plantImage)
    }

}