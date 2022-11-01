package com.charaminstra.pleon.feed.guide

import com.charaminstra.pleon.feed.databinding.ItemDefaultNotiBinding
import com.charaminstra.pleon.foundation.model.GuideData

class DefaultNotiViewHolder (private val binding: ItemDefaultNotiBinding): GuideCommonViewHolder(binding) {
    override fun bind(item: GuideData, onClickNoti: (String, String) -> Unit) {
        binding.notiTv.text = item.content
    }
}