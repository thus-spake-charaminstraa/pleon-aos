package com.charaminstra.pleon.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.adapter.ActionObject
import com.charaminstra.pleon.adapter.ActionType
import com.charaminstra.pleon.databinding.ItemFeedBinding
import com.charaminstra.pleon.databinding.ItemPlantFeedBinding

class ActionViewHolder(
    private val binding: ItemPlantFeedBinding,
    private var onItemClicked: (ActionType) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ActionObject) {
        //binding.plantImage
        binding.plantName.text = item.actionType.toString()
        binding.root.setOnClickListener {
            onItemClicked(item.actionType)
        }
        Glide.with(binding.root).load(item.actionImage).circleCrop().into(binding.plantImage)
    }

}