package com.charaminstra.pleon.feed.guide


import com.charaminstra.pleon.feed.databinding.ItemGuideTwoBtnBinding
import com.charaminstra.pleon.common.data.GuideData

const val NOTI_LATER = "noti_later"
const val NOTI_COMPLETE = "noti_complete"

class TwoBtnNotiViewHolder (private val binding: ItemGuideTwoBtnBinding): GuideCommonViewHolder(binding) {
    override fun bind(item: GuideData, onClickNoti: (String, String) -> Unit) {
        binding.notiTv.text = item.content
        binding.notiLaterBtn.setOnClickListener {
            onClickNoti(item.id, NOTI_LATER)
        }
        binding.notiCompleteBtn.setOnClickListener {
            onClickNoti(item.id, NOTI_COMPLETE)
        }
    }
}