package com.charaminstra.pleon.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.charaminstra.pleon.PlantsViewModel
import com.charaminstra.pleon.foundation.model.PlantDataObject


abstract class CommonViewHolder(
    binding: ViewBinding,
): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(viewObject: PlantDataObject, itemList: List<PlantDataObject>)
}


