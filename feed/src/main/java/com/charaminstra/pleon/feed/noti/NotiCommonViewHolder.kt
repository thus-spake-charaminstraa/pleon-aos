package com.charaminstra.pleon.feed.noti

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.charaminstra.pleon.foundation.model.NotiData

abstract class NotiCommonViewHolder (binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
    open fun bind(viewObject: NotiData) {}
}
