package com.charaminstra.pleon.feed.noti

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.feed.databinding.*
import com.charaminstra.pleon.foundation.model.NotiViewTypeData

enum class NotiViewType{
    DATE, TEXT, IMAGE
}

class NotiAdapter(): ListAdapter<NotiViewTypeData, RecyclerView.ViewHolder>(NotiDiffCallback()){

    override fun getItemViewType(position: Int): Int {
        return NotiViewType.valueOf(currentList[position].viewType).ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiCommonViewHolder {
        return when(viewType){
            NotiViewType.DATE.ordinal -> NotiDateViewHolder(
                ItemNotiDateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
            NotiViewType.TEXT.ordinal -> NotiTextViewHolder(
                ItemNotiTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
            NotiViewType.IMAGE.ordinal -> NotiImageViewHolder(
                ItemNotiImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
            else -> throw IllegalArgumentException("Unknown view type")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val notiViewTypeData = getItem(position)
        (holder as NotiCommonViewHolder).bind(notiViewTypeData.viewObject)
    }

}

private class NotiDiffCallback : DiffUtil.ItemCallback<NotiViewTypeData>() {

    override fun areItemsTheSame(oldItem: NotiViewTypeData, newItem: NotiViewTypeData): Boolean {
        return oldItem.viewObject.id == newItem.viewObject.id
    }

    override fun areContentsTheSame(oldItem: NotiViewTypeData, newItem: NotiViewTypeData): Boolean {
        return oldItem == newItem
    }
}