package com.charaminstra.pleon.feed.noti

import com.charaminstra.pleon.feed.databinding.ItemDefaultNotiBinding
import com.charaminstra.pleon.foundation.model.NotiData

class DefaultNotiViewHolder (private val binding: ItemDefaultNotiBinding): NotiCommonViewHolder(binding) {
    override fun bind(item: NotiData, onClickNoti: (String, String) -> Unit) {
        binding.notiTv.text = item.content
    }
}