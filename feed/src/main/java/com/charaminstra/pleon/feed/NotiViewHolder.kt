package com.charaminstra.pleon.feed

import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.feed.databinding.ItemNotiBinding
import com.charaminstra.pleon.foundation.model.NotiData

const val NOTI_LATER = "noti_later"
const val NOTI_COMPLETE = "noti_complete"

class NotiViewHolder (private val binding: ItemNotiBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: NotiData, onClickNoti: (String, String) -> Unit) {
        binding.notiTv.text = item.content
        binding.notiLaterBtn.setOnClickListener {
            onClickNoti(item.id, NOTI_LATER)
        }
        binding.notiCompleteBtn.setOnClickListener {
            onClickNoti(item.id, NOTI_LATER)
        }
    }
}