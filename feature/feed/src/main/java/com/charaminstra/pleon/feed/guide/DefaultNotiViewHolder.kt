package com.charaminstra.pleon.feed.guide

import com.charaminstra.pleon.feed.databinding.ItemGuideDefaultBinding
import com.charaminstra.pleon.common.model.GuideData

class DefaultNotiViewHolder (private val binding: ItemGuideDefaultBinding): GuideCommonViewHolder(binding) {
    override fun bind(item: GuideData, onClickNoti: (String, String) -> Unit) {
        binding.notiTv.text = item.content
    }
}