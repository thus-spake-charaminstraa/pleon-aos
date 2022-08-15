package com.charaminstra.pleon.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.adapter.ActionObject
import com.charaminstra.pleon.adapter.ActionType
import com.charaminstra.pleon.databinding.ItemFeedBinding
import com.charaminstra.pleon.databinding.ItemPlantFeedBinding

class ActionViewHolder(private val binding: ItemPlantFeedBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ActionObject, onItemClicked: (ActionType) -> Unit) {
        binding.plantName.text = item.actionType.toString()
        binding.plantImage.setOnClickListener {
            Log.i("ha action","click click")
            onItemClicked(item.actionType)
        }
        Glide.with(binding.root).load(item.actionImage).circleCrop().into(binding.plantImage)
    }

}