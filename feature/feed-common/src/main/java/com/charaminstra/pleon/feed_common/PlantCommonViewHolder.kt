package com.charaminstra.pleon.feed_common

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.charaminstra.pleon.domain.model.Plant

abstract class PlantCommonViewHolder(
    binding: ViewBinding,
): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(viewObject: Plant, onItemClicked: (String) -> Unit)
}