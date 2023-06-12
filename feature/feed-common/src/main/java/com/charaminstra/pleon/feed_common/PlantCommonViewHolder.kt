package com.charaminstra.pleon.feed_common

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.charaminstra.pleon.common.data.PlantDataObject

abstract class PlantCommonViewHolder(
    binding: ViewBinding,
): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(viewObject: PlantDataObject, onItemClicked: (String) -> Unit)
}