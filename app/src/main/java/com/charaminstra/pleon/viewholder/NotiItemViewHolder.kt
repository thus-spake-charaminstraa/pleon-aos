package com.charaminstra.pleon.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.charaminstra.pleon.common_ui.R
import com.charaminstra.pleon.databinding.ItemNotiBinding
import com.charaminstra.pleon.foundation.model.FeedObject
import com.charaminstra.pleon.foundation.model.ResultObject
import com.charaminstra.pleon.foundation.model.ViewObject
import java.text.SimpleDateFormat

class NotiItemViewHolder (
    private val binding: ItemNotiBinding,
    private var onItemClicked: (String) -> Unit): FeedTabCommonViewHolder(binding){
    override fun bind(item: ViewObject) {

    }
}