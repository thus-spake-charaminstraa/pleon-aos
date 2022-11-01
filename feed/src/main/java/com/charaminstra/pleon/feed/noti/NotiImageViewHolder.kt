package com.charaminstra.pleon.feed.noti

import com.charaminstra.pleon.feed.databinding.ItemNotiImageBinding
import com.charaminstra.pleon.foundation.model.NotiData


class NotiImageViewHolder (private val binding: ItemNotiImageBinding): NotiCommonViewHolder(binding) {
    override fun bind(item: NotiData) {
        binding.notiTitle.text = item.content
//        binding.notiTitle.text = item.
//        binding.notiCompleteBtn.setOnClickListener {
//            onClickNoti(item.id, NOTI_COMPLETE)
//        }
    }
}