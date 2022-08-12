package com.charaminstra.pleon.viewholder

import com.charaminstra.pleon.NOTI_COMPLETE
import com.charaminstra.pleon.NOTI_LATER
import com.charaminstra.pleon.databinding.ItemNotiBinding
import com.charaminstra.pleon.foundation.model.ViewObject

class NotiItemViewHolder (
    private val binding: ItemNotiBinding): FeedTabCommonViewHolder(binding){
    override fun bind(item: ViewObject, onItemClicked: (String) -> Unit) {
        binding.notiTv.text= item.content
        binding.laterBtn.setOnClickListener {
            onItemClicked(NOTI_LATER)
        }
        binding.completeBtn.setOnClickListener {
            onItemClicked(NOTI_COMPLETE)
        }
    }
}