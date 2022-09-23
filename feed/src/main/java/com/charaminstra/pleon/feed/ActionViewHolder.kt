package com.charaminstra.pleon.feed

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.common.ActionObject
import com.charaminstra.pleon.common.ActionType
import com.charaminstra.pleon.feed.databinding.ItemActionBinding

class ActionViewHolder(val binding: ItemActionBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ActionObject) {
        binding.actionName.text = item.actionType.toString()
        Glide.with(binding.root).load(item.actionImage).into(binding.actionImg)
    }
}