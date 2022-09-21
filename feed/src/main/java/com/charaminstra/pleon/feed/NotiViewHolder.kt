package com.charaminstra.pleon.feed

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.common.PlantCommonViewHolder
import com.charaminstra.pleon.feed.databinding.ItemNotiBinding
import com.charaminstra.pleon.feed.databinding.ItemPlantFilterBinding
import com.charaminstra.pleon.foundation.model.NotiData
import com.charaminstra.pleon.foundation.model.PlantDataObject

class NotiViewHolder (private val binding: ItemNotiBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: NotiData, onItemClicked: (String) -> Unit) {
        binding.notiTv.text = item.content
    }
}