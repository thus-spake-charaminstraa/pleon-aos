package com.charaminstra.pleon.feed.noti

import com.bumptech.glide.Glide
import com.charaminstra.pleon.feed.databinding.ItemNotiImageBinding
import com.charaminstra.pleon.foundation.model.NotiData


class NotiImageViewHolder (private val binding: ItemNotiImageBinding): NotiCommonViewHolder(binding) {
    override fun bind(item: NotiData) {
        binding.notiTitle.text = item.content
        Glide.with(binding.root).load(item.feedImageUrl).into(binding.notiImage)
    }
}