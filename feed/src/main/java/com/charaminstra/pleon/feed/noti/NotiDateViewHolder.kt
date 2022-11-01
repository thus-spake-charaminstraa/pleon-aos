package com.charaminstra.pleon.feed.noti

import com.charaminstra.pleon.feed.databinding.ItemNotiDateBinding
import com.charaminstra.pleon.foundation.model.NotiData

class NotiDateViewHolder (private val binding: ItemNotiDateBinding): NotiCommonViewHolder(binding) {
    override fun bind(item: NotiData) {
        binding.notiTitle.text = item.text
//        binding.notiCompleteBtn.setOnClickListener {
//            onClickNoti(item.id, NOTI_COMPLETE)
//        }
    }
}