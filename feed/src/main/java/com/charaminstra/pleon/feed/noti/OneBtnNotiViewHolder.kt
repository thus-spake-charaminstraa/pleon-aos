package com.charaminstra.pleon.feed.noti

import com.charaminstra.pleon.feed.databinding.ItemOneBtnNotiBinding
import com.charaminstra.pleon.foundation.model.NotiData

class OneBtnNotiViewHolder (private val binding: ItemOneBtnNotiBinding): NotiCommonViewHolder(binding) {
    override fun bind(item: NotiData, onClickNoti: (String, String) -> Unit) {
        binding.notiTv.text = item.content
//        binding.notiLaterBtn.setOnClickListener {
//            onClickNoti(item.id, NOTI_LATER)
//        }
//        binding.notiCompleteBtn.setOnClickListener {
//            onClickNoti(item.id, NOTI_COMPLETE)
//        }
    }
}