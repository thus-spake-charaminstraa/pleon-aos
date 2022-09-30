package com.charaminstra.pleon.feed.noti

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.feed.databinding.ItemDefaultNotiBinding
import com.charaminstra.pleon.feed.databinding.ItemOneBtnNotiBinding
import com.charaminstra.pleon.feed.databinding.ItemTwoBtnNotiBinding
import com.charaminstra.pleon.foundation.model.NotiViewTypeData

class NotiAdapter(): RecyclerView.Adapter<NotiCommonViewHolder>() {

    var viewItemList: List<NotiViewTypeData> = listOf()
    var onClickNoti: (String, String)-> Unit = { s1: String, s2: String -> }

    override fun getItemViewType(position: Int): Int {
        return NotiViewType.valueOf(viewItemList[position].viewType).ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiCommonViewHolder {
        return when(viewType){
            NotiViewType.TWO_BTN.ordinal -> TwoBtnNotiViewHolder(
                ItemTwoBtnNotiBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
            NotiViewType.ONE_BTN.ordinal -> OneBtnNotiViewHolder(
                    ItemOneBtnNotiBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
            )
            NotiViewType.DEFAULT.ordinal -> DefaultNotiViewHolder(
                ItemDefaultNotiBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
            else -> throw IllegalArgumentException("Unknown view type")
        }

    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }

    override fun onBindViewHolder(holder: NotiCommonViewHolder, position: Int) {
        holder.bind(viewItemList[position].viewObject,onClickNoti)
    }

    fun refreshItems(viewItemList : List<NotiViewTypeData>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }
}
