package com.charaminstra.pleon.feed.noti

import com.charaminstra.pleon.feed.databinding.ItemNotiTextBinding
import com.charaminstra.pleon.foundation.model.NotiData


class NotiTextViewHolder (private val binding: ItemNotiTextBinding): NotiCommonViewHolder(binding) {
    override fun bind(item: NotiData) {
        binding.notiTitle.text = item.content
//        binding.notiTitle.text = item.
//        binding.notiCompleteBtn.setOnClickListener {
//            onClickNoti(item.id, NOTI_COMPLETE)
//        }
    }
}