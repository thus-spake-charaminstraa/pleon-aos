package com.charaminstra.pleon.feed.guide

import com.charaminstra.pleon.feed.databinding.ItemGuideOneBtnBinding
import com.charaminstra.pleon.foundation.model.GuideData

const val NOTI_GO = "NOTI_GO"

class OneBtnNotiViewHolder (private val binding: ItemGuideOneBtnBinding): GuideCommonViewHolder(binding) {
    override fun bind(item: GuideData, onClickNoti: (String, String) -> Unit) {
        binding.notiTv.text = item.content
        binding.notiGoBtn.setOnClickListener {
            onClickNoti("plant_register", NOTI_GO)
        }
//        binding.notiCompleteBtn.setOnClickListener {
//            onClickNoti(item.id, NOTI_COMPLETE)
//        }
    }
}