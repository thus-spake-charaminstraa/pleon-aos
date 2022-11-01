package com.charaminstra.pleon.feed.noti

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.feed.databinding.*
import com.charaminstra.pleon.feed.guide.*
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


//    var viewItemList: List<NotiViewTypeData> = listOf()
//    var onClickNoti: (String, String)-> Unit = { s1: String, s2: String -> }
//
//    override fun getItemViewType(position: Int): Int {
//        return GuideViewType.valueOf(viewItemList[position].viewType).ordinal
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideCommonViewHolder {
//        return when(viewType){
//            GuideViewType.TWO_BTN.ordinal -> TwoBtnNotiViewHolder(
//                ItemTwoBtnNotiBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent, false
//                )
//            )
//            GuideViewType.ONE_BTN.ordinal -> OneBtnNotiViewHolder(
//                ItemOneBtnNotiBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent, false
//                )
//            )
//            GuideViewType.DEFAULT.ordinal -> DefaultNotiViewHolder(
//                ItemDefaultNotiBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent, false
//                )
//            )
//            else -> throw IllegalArgumentException("Unknown view type")
//        }
//
//    }
//
//    override fun getItemCount(): Int {
//        return viewItemList.size
//    }
//
//    override fun onBindViewHolder(holder: GuideCommonViewHolder, position: Int) {
//        holder.bind(viewItemList[position].viewObject, onClickNoti)
//    }
//
//    fun refreshItems(viewItemList : List<NotiViewTypeData>) {
//        this.viewItemList = viewItemList
//        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
//    }
//}
