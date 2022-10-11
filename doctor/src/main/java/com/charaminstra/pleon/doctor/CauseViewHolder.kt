package com.charaminstra.pleon.doctor

import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.doctor.databinding.ItemCauseBinding
import com.charaminstra.pleon.foundation.model.CauseObject

class CauseViewHolder(val binding: ItemCauseBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CauseObject, position: Int) {
        binding.causeNum.text = (position+1).toString()
        binding.causeTxt.text = item.cause_ko
    }
}