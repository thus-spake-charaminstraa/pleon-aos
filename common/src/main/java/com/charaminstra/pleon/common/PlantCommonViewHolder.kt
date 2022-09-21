package com.charaminstra.pleon.common

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.charaminstra.pleon.foundation.model.PlantDataObject

abstract class PlantCommonViewHolder(
    binding: ViewBinding,
): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(viewObject: PlantDataObject, onItemClicked: (String) -> Unit)
}

