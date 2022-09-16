package com.charaminstra.pleon.common

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.charaminstra.pleon.foundation.api.PleonPreference
import com.charaminstra.pleon.foundation.model.FeedObject
import com.charaminstra.pleon.foundation.model.PlantDataObject
import com.charaminstra.pleon.foundation.model.ViewObject


abstract class CommonViewHolder(
    binding: ViewBinding,
): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(viewObject: PlantDataObject, onItemClicked: (String) -> Unit)
}

abstract class FeedCommonViewHolder(
    binding: ViewBinding
): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(viewObject: ViewObject,
                      onClickFeed: (String) -> Unit,
                      onClickNoti: (String, String) -> Unit)
}


