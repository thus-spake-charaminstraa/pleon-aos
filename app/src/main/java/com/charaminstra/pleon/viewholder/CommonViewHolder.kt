package com.charaminstra.pleon.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.charaminstra.pleon.foundation.api.PleonPreference
import com.charaminstra.pleon.foundation.model.PlantDataObject
import com.charaminstra.pleon.foundation.model.ResultObject


abstract class CommonViewHolder(
    binding: ViewBinding,
): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(viewObject: PlantDataObject)
}


abstract class FeedItemCommonViewHolder(
    binding: ViewBinding,
): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(viewObject: ResultObject)
}

