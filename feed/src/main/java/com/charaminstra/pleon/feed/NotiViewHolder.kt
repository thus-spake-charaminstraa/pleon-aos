package com.charaminstra.pleon.feed

import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.feed.databinding.ItemNotiBinding
import com.charaminstra.pleon.foundation.model.NotiData

class NotiViewHolder (private val binding: ItemNotiBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: NotiData, onItemClicked: (String) -> Unit) {
        binding.notiTv.text = item.content
    }
}