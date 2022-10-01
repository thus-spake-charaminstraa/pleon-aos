package com.charaminstra.pleon.feed.noti

import com.charaminstra.pleon.feed.databinding.ItemOneBtnNotiBinding
import com.charaminstra.pleon.foundation.model.NotiData

const val NOTI_GO = "NOTI_GO"

class OneBtnNotiViewHolder (private val binding: ItemOneBtnNotiBinding): NotiCommonViewHolder(binding) {
    override fun bind(item: NotiData, onClickNoti: (String, String) -> Unit) {
        binding.notiTv.text = item.content
        binding.notiGoBtn.setOnClickListener {
            onClickNoti("plant_register", NOTI_GO)
        }
//        binding.notiCompleteBtn.setOnClickListener {
//            onClickNoti(item.id, NOTI_COMPLETE)
//        }
    }
}