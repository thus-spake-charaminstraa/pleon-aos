package com.charaminstra.pleon.common.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.common.FeedCommonViewHolder
import com.charaminstra.pleon.common.FeedViewType
import com.charaminstra.pleon.common.FeedItemViewHolder
import com.charaminstra.pleon.common.databinding.ItemFeedBinding
import com.charaminstra.pleon.foundation.model.ResultObject

class FeedAdapter(): RecyclerView.Adapter<FeedCommonViewHolder>() {

    var viewItemList: List<ResultObject> = listOf()
    var onClickFeed: (String)-> Unit = {}
    var onClickNoti: (String, String)-> Unit = { feedId: String, button: String -> }

    override fun getItemViewType(position: Int): Int {
        return FeedViewType.valueOf(viewItemList[position].viewType).ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedCommonViewHolder {
        return when(viewType){
            FeedViewType.feed.ordinal -> FeedItemViewHolder(ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            //FeedViewType.noti.ordinal -> NotiItemViewHolder(ItemNotiBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }
    override fun onBindViewHolder(holder: FeedCommonViewHolder, position: Int) {
        holder.bind(viewItemList[position].viewObject, onClickFeed, onClickNoti)
    }

    fun refreshItems(viewItemList : List<ResultObject>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }
}
