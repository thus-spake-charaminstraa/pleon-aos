package com.charaminstra.pleon.feed_common

import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.common.model.CauseObject
import com.charaminstra.pleon.feed_common.databinding.ItemCauseBinding

class CauseViewHolder(val binding: ItemCauseBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CauseObject, position: Int) {
        binding.causeNum.text = (position+1).toString()
        binding.causeTxt.text = item.cause_ko
    }
}