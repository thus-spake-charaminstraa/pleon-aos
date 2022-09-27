package com.charaminstra.pleon.feed.noti

import android.widget.SimpleAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.charaminstra.pleon.feed.databinding.ItemTwoBtnNotiBinding
import com.charaminstra.pleon.foundation.model.NotiData

abstract class NotiCommonViewHolder (binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
    open fun bind(viewObject: NotiData, onClickNoti: (String, String) -> Unit) {
    }
}