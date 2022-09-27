package com.charaminstra.pleon.feed.noti


import com.charaminstra.pleon.feed.databinding.ItemTwoBtnNotiBinding
import com.charaminstra.pleon.foundation.model.NotiData

const val NOTI_LATER = "noti_later"
const val NOTI_COMPLETE = "noti_complete"

class TwoBtnNotiViewHolder (private val binding: ItemTwoBtnNotiBinding): NotiCommonViewHolder(binding) {
    override fun bind(item: NotiData, onClickNoti: (String, String) -> Unit) {
        binding.notiTv.text = item.content
        binding.notiLaterBtn.setOnClickListener {
            onClickNoti(item.id, NOTI_LATER)
        }
        binding.notiCompleteBtn.setOnClickListener {
            onClickNoti(item.id, NOTI_COMPLETE)
        }
    }
}