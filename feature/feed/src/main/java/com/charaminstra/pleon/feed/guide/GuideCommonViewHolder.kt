package com.charaminstra.pleon.feed.guide

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.charaminstra.pleon.common.model.GuideData

abstract class GuideCommonViewHolder (binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
    open fun bind(viewObject: GuideData, onClickNoti: (String, String) -> Unit) {
    }
}