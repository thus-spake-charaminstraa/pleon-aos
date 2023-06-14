package com.charaminstra.pleon.feed_common

import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.common.model.CauseObject
import com.charaminstra.pleon.feed_common.databinding.ItemSolutionBinding

class SolutionViewHolder(val binding: ItemSolutionBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CauseObject, position: Int) {
        binding.solutionNum.text = (position+1).toString()
        binding.solutionTxt.text = item.guide
    }
}